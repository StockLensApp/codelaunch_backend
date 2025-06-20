# SEC/SEDAR Filing Sentiment Analysis - Kotlin Spring Boot Implementation Guide

## Overview
This guide provides a focused backend implementation for extracting and analyzing SEC/SEDAR filing insights using Kotlin Spring Boot with coroutines. The system generates a 0-100 sentiment score with three key signals: Risk Acceleration, Management Confidence, and Material Event Velocity.

## API Access & Pricing

### US SEC EDGAR (FREE)
- **Base URL**: `https://data.sec.gov/`
- **Rate Limit**: 10 requests/second
- **Authentication**: None required, but must set User-Agent header
- **Cost**: FREE
- **Bulk Downloads**: Available at `https://www.sec.gov/Archives/edgar/daily-index/`

### Canadian SEDAR+ (NO API)
- **Access Method**: Web scraping only
- **Base URL**: `https://www.sedarplus.ca/`
- **Cost**: FREE to access, but no API
- **Alternative**: Commercial APIs (QuoteMedia ~$500-2000/month)
- **Recommendation**: Start with JSoup web scraping for MVP

## Core Implementation

### 1. Common Models and Configuration

```kotlin
// config/FilingConfig.kt
package app.stocklens.secfilings.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "stocklens.filings")
data class FilingConfig(
    val sec: SecConfig = SecConfig(),
    val sedar: SedarConfig = SedarConfig(),
    val cache: CacheConfig = CacheConfig(),
    val finbert: FinbertConfig = FinbertConfig()
)

data class SecConfig(
    val baseUrl: String = "https://data.sec.gov",
    val userAgent: String = "StockLens support@stocklens.com",
    val rateLimit: Int = 10
)

data class SedarConfig(
    val baseUrl: String = "https://www.sedarplus.ca",
    val scrapeDelay: Duration = Duration.ofSeconds(2)
)

data class CacheConfig(
    val ttl: Map<String, Duration> = mapOf(
        "10-K" to Duration.ofDays(30),
        "10-Q" to Duration.ofDays(7),
        "8-K" to Duration.ofDays(1),
        "AIF" to Duration.ofDays(30),
        "MD&A" to Duration.ofDays(7),
        "Material Change" to Duration.ofDays(1)
    )
)

data class FinbertConfig(
    val prosusaiUrl: String = "http://your-prosusai-endpoint",
    val yiyanghkustUrl: String = "http://your-yiyanghkust-endpoint"
)

// domain/FilingTypes.kt
package app.stocklens.secfilings.domain

enum class FilingType(val displayName: String, val market: String) {
    // US Types
    FORM_10K("10-K", "US"),
    FORM_10Q("10-Q", "US"),
    FORM_8K("8-K", "US"),
    // Canadian Types
    AIF("AIF", "CA"),
    MD_AND_A("MD&A", "CA"),
    MATERIAL_CHANGE("Material Change Report", "CA")
}

// dto/SecFilingsInsightsDto.kt
package app.stocklens.secfilings.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SecFilingsInsightsDto(
    val ticker: String,
    val market: String,
    val sentimentScore: Double,  // 0-100
    val confidence: String,
    val signals: SentimentSignals = SentimentSignals(),
    val timeHorizon: TimeHorizon = TimeHorizon(),
    val keyInsights: List<String> = emptyList(),
    val lastUpdated: LocalDateTime = LocalDateTime.now()
)

@Serializable
data class SentimentSignals(
    val riskAcceleration: Double = 50.0,
    val managementConfidence: Double = 50.0,
    val eventVelocity: Double = 50.0
)

@Serializable
data class TimeHorizon(
    val shortTerm: Int = 50,  // 0-100
    val longTerm: Int = 50    // 0-100
)

// dto/Filing.kt
package app.stocklens.secfilings.dto

import java.time.LocalDate

data class Filing(
    val formType: String,
    val filingDate: LocalDate,
    val accessionNumber: String? = null,
    val primaryDocument: String? = null,
    val url: String? = null,
    val cik: String? = null
)

data class FilingSection(
    val name: String,
    val content: String
)
```

### 2. Base Filing Analyzer

```kotlin
// service/BaseFilingAnalyzer.kt
package app.stocklens.secfilings.service

import app.stocklens.secfilings.dto.Filing
import app.stocklens.secfilings.dto.FilingSection
import app.stocklens.secfilings.dto.SecFilingsInsightsDto
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

abstract class BaseFilingAnalyzer(
    protected val webClient: WebClient,
    protected val redisTemplate: ReactiveRedisTemplate<String, String>
) {
    abstract suspend fun fetchFilings(ticker: String, filingTypes: List<String>): List<Filing>
    abstract suspend fun extractSections(filingContent: String): List<FilingSection>
    abstract suspend fun getFilingContent(filing: Filing): String
    
    suspend fun getCachedSentiment(cacheKey: String): SecFilingsInsightsDto? {
        return redisTemplate.opsForValue()
            .get(cacheKey)
            .map { json ->
                // Deserialize from JSON using kotlinx.serialization
                kotlinx.serialization.json.Json.decodeFromString<SecFilingsInsightsDto>(json)
            }
            .awaitSingle()
    }
    
    suspend fun cacheSentiment(
        cacheKey: String,
        sentiment: SecFilingsInsightsDto,
        ttl: Duration
    ) {
        val json = kotlinx.serialization.json.Json.encodeToString(
            SecFilingsInsightsDto.serializer(),
            sentiment
        )
        
        redisTemplate.opsForValue()
            .set(cacheKey, json, ttl)
            .awaitSingle()
    }
}
```

### 3. SEC EDGAR Implementation

```kotlin
// service/sec/SECAnalyzer.kt
package app.stocklens.secfilings.service.sec

import app.stocklens.secfilings.config.FilingConfig
import app.stocklens.secfilings.dto.Filing
import app.stocklens.secfilings.dto.FilingSection
import app.stocklens.secfilings.service.BaseFilingAnalyzer
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.serialization.json.*
import org.jsoup.Jsoup
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class SECAnalyzer(
    webClient: WebClient,
    redisTemplate: ReactiveRedisTemplate<String, String>,
    private val config: FilingConfig
) : BaseFilingAnalyzer(webClient, redisTemplate) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun fetchFilings(ticker: String, filingTypes: List<String>): List<Filing> {
        // Step 1: Get CIK from ticker
        val cik = getCIKFromTicker(ticker)
            ?: throw IllegalArgumentException("CIK not found for ticker $ticker")
        
        // Step 2: Get submissions for CIK
        val submissionsUrl = "${config.sec.baseUrl}/submissions/CIK${cik.padStart(10, '0')}.json"
        
        val submissions = webClient.get()
            .uri(submissionsUrl)
            .header("User-Agent", config.sec.userAgent)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
        
        // Step 3: Parse and filter filings
        return parseSubmissions(submissions, filingTypes, cik)
    }
    
    private suspend fun getCIKFromTicker(ticker: String): String? {
        val tickersUrl = "${config.sec.baseUrl}/files/company_tickers.json"
        
        val response = webClient.get()
            .uri(tickersUrl)
            .header("User-Agent", config.sec.userAgent)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
        
        val tickers = json.parseToJsonElement(response).jsonObject
        
        tickers.values.forEach { element ->
            val obj = element.jsonObject
            if (obj["ticker"]?.jsonPrimitive?.content == ticker.uppercase()) {
                return obj["cik_str"]?.jsonPrimitive?.content
            }
        }
        
        return null
    }
    
    private fun parseSubmissions(
        submissionsJson: String,
        filingTypes: List<String>,
        cik: String
    ): List<Filing> {
        val submissions = json.parseToJsonElement(submissionsJson).jsonObject
        val recent = submissions["filings"]?.jsonObject?.get("recent")?.jsonObject
            ?: return emptyList()
        
        val forms = recent["form"]?.jsonArray ?: return emptyList()
        val filingDates = recent["filingDate"]?.jsonArray ?: return emptyList()
        val accessionNumbers = recent["accessionNumber"]?.jsonArray ?: return emptyList()
        val primaryDocuments = recent["primaryDocument"]?.jsonArray ?: return emptyList()
        
        val filings = mutableListOf<Filing>()
        
        for (i in forms.indices) {
            val formType = forms[i].jsonPrimitive.content
            
            if (formType in filingTypes) {
                filings.add(
                    Filing(
                        formType = formType,
                        filingDate = LocalDate.parse(
                            filingDates[i].jsonPrimitive.content,
                            DateTimeFormatter.ISO_DATE
                        ),
                        accessionNumber = accessionNumbers[i].jsonPrimitive.content,
                        primaryDocument = primaryDocuments[i].jsonPrimitive.content,
                        cik = cik
                    )
                )
                
                if (filings.size >= 10) break
            }
        }
        
        return filings
    }
    
    override suspend fun extractSections(filingContent: String): List<FilingSection> {
        val doc = Jsoup.parse(filingContent)
        val text = doc.text()
        
        val sections = mutableListOf<FilingSection>()
        
        // Extract Risk Factors
        val riskPattern = Regex(
            """Item\s*1A\.?\s*Risk\s*Factors(.*?)Item\s*1B""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        riskPattern.find(text)?.let { match ->
            sections.add(
                FilingSection(
                    "risk_factors",
                    match.groupValues[1].trim().take(50000)
                )
            )
        }
        
        // Extract MD&A
        val mdaPattern = Regex(
            """Item\s*7\.?\s*Management.*?Discussion.*?Analysis(.*?)Item\s*7A""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        mdaPattern.find(text)?.let { match ->
            sections.add(
                FilingSection(
                    "md_a",
                    match.groupValues[1].trim().take(50000)
                )
            )
        }
        
        // Extract Forward Looking Statements
        val forwardPattern = Regex(
            """forward[\s-]?looking\s+statements?(.*?)(?:Item|ITEM|$)""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        forwardPattern.find(text)?.let { match ->
            sections.add(
                FilingSection(
                    "forward_looking",
                    match.groupValues[1].trim().take(10000)
                )
            )
        }
        
        return sections
    }
    
    override suspend fun getFilingContent(filing: Filing): String {
        val accessionNoDash = filing.accessionNumber?.replace("-", "")
        val url = "${config.sec.baseUrl}/Archives/edgar/data/${filing.cik}/" +
                 "$accessionNoDash/${filing.primaryDocument}"
        
        return webClient.get()
            .uri(url)
            .header("User-Agent", config.sec.userAgent)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
    }
}
```

### 4. Canadian SEDAR Implementation

```kotlin
// service/sedar/SEDARAnalyzer.kt
package app.stocklens.secfilings.service.sedar

import app.stocklens.secfilings.config.FilingConfig
import app.stocklens.secfilings.dto.Filing
import app.stocklens.secfilings.dto.FilingSection
import app.stocklens.secfilings.service.BaseFilingAnalyzer
import kotlinx.coroutines.delay
import org.jsoup.Jsoup
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class SEDARAnalyzer(
    webClient: WebClient,
    redisTemplate: ReactiveRedisTemplate<String, String>,
    private val config: FilingConfig
) : BaseFilingAnalyzer(webClient, redisTemplate) {
    
    override suspend fun fetchFilings(ticker: String, filingTypes: List<String>): List<Filing> {
        // For MVP, we'll use JSoup to scrape SEDAR+
        // In production, consider using a commercial API
        
        val searchUrl = "${config.sedar.baseUrl}/csa-party/profiles/new-search"
        delay(config.sedar.scrapeDelay.toMillis()) // Respectful scraping
        
        // Note: This is simplified. Real implementation would need more robust scraping
        val searchPage = Jsoup.connect(searchUrl)
            .userAgent("Mozilla/5.0")
            .get()
        
        // Search for company and get profile URL
        // This would involve form submission and navigation
        // For hackathon, you might want to hardcode some test URLs
        
        return listOf(
            // Mock data for testing
            Filing(
                formType = "AIF",
                filingDate = LocalDate.now().minusDays(30),
                url = "${config.sedar.baseUrl}/filings/mock-aif"
            ),
            Filing(
                formType = "MD&A",
                filingDate = LocalDate.now().minusDays(7),
                url = "${config.sedar.baseUrl}/filings/mock-mda"
            )
        )
    }
    
    override suspend fun extractSections(filingContent: String): List<FilingSection> {
        val sections = mutableListOf<FilingSection>()
        
        // Canadian filing patterns
        val riskPattern = Regex(
            """(?:RISK\s+FACTORS|RISKS?\s+AND\s+UNCERTAINTIES)(.*?)(?:ITEM|SECTION|$)""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        
        val mdaPattern = Regex(
            """MANAGEMENT.*?DISCUSSION.*?ANALYSIS(.*?)(?:FINANCIAL\s+STATEMENTS|$)""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        
        val forwardPattern = Regex(
            """(?:FORWARD[\s-]?LOOKING|CAUTIONARY)\s+STATEMENT(.*?)(?:RISK|$)""",
            RegexOption.DOT_MATCHES_ALL or RegexOption.IGNORE_CASE
        )
        
        val patterns = mapOf(
            "risk_factors" to riskPattern,
            "md_a" to mdaPattern,
            "forward_looking" to forwardPattern
        )
        
        patterns.forEach { (name, pattern) ->
            pattern.find(filingContent)?.let { match ->
                sections.add(
                    FilingSection(
                        name,
                        match.groupValues[1].trim().take(50000)
                    )
                )
            }
        }
        
        return sections
    }
    
    override suspend fun getFilingContent(filing: Filing): String {
        // For SEDAR, we would fetch the filing from the URL
        // This might be PDF or HTML
        filing.url?.let { url ->
            delay(config.sedar.scrapeDelay.toMillis())
            
            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .get()
            
            return doc.text()
        }
        
        return ""
    }
}
```

### 5. Sentiment Analysis Service

```kotlin
// service/SentimentAnalysisService.kt
package app.stocklens.secfilings.service

import app.stocklens.secfilings.config.FilingConfig
import app.stocklens.secfilings.dto.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

@Service
class SentimentAnalysisService(
    private val webClient: WebClient,
    private val config: FilingConfig
) {
    
    @Serializable
    data class FinBertRequest(val text: String)
    
    @Serializable
    data class FinBertResponse(
        val positive: Double,
        val negative: Double,
        val neutral: Double
    )
    
    suspend fun analyzeFilings(
        ticker: String,
        market: String,
        sections: List<FilingSection>,
        historicalFilings: List<Filing>
    ): SecFilingsInsightsDto = coroutineScope {
        
        // 1. Analyze each section with FinBERT
        val sectionSentiments = sections.map { section ->
            async {
                section to analyzeSectionSentiment(section)
            }
        }.awaitAll().toMap()
        
        // 2. Calculate three signals
        val signals = calculateSignals(sections, sectionSentiments, historicalFilings)
        
        // 3. Generate overall score
        val overallScore = calculateOverallScore(sectionSentiments, signals)
        
        // 4. Determine time horizon
        val timeHorizon = determineTimeHorizon(sections, signals)
        
        // 5. Extract key insights
        val insights = extractKeyInsights(sections, sectionSentiments)
        
        SecFilingsInsightsDto(
            ticker = ticker,
            market = market,
            sentimentScore = overallScore,
            confidence = calculateConfidence(sectionSentiments),
            signals = signals,
            timeHorizon = timeHorizon,
            keyInsights = insights,
            lastUpdated = LocalDateTime.now()
        )
    }
    
    private suspend fun analyzeSectionSentiment(section: FilingSection): FinBertResponse {
        // Choose model based on section type
        val modelUrl = when (section.name) {
            "risk_factors" -> config.finbert.prosusaiUrl
            else -> config.finbert.yiyanghkustUrl
        }
        
        // Chunk content for FinBERT (512 token limit)
        val chunks = chunkText(section.content, 500)
        
        val sentiments = coroutineScope {
            chunks.take(10).map { chunk ->
                async {
                    webClient.post()
                        .uri(modelUrl)
                        .bodyValue(FinBertRequest(chunk))
                        .retrieve()
                        .bodyToMono<FinBertResponse>()
                        .awaitSingle()
                }
            }.awaitAll()
        }
        
        // Aggregate sentiments
        return aggregateSentiments(sentiments)
    }
    
    private fun chunkText(text: String, maxWords: Int): List<String> {
        val words = text.split(Regex("\\s+"))
        return words.chunked(maxWords).map { it.joinToString(" ") }
    }
    
    private fun aggregateSentiments(sentiments: List<FinBertResponse>): FinBertResponse {
        if (sentiments.isEmpty()) {
            return FinBertResponse(0.33, 0.33, 0.34)
        }
        
        val avgPositive = sentiments.map { it.positive }.average()
        val avgNegative = sentiments.map { it.negative }.average()
        val avgNeutral = sentiments.map { it.neutral }.average()
        
        return FinBertResponse(avgPositive, avgNegative, avgNeutral)
    }
    
    private fun calculateSignals(
        sections: List<FilingSection>,
        sentiments: Map<FilingSection, FinBertResponse>,
        historicalFilings: List<Filing>
    ): SentimentSignals {
        // Risk Acceleration
        val riskScore = sections.find { it.name == "risk_factors" }?.let { riskSection ->
            val currentRiskCount = riskSection.content.split(".").size
            // Simplified: would need to fetch historical risk sections
            val historicalAvg = 100.0 // Mock for hackathon
            val riskDelta = (currentRiskCount - historicalAvg) / historicalAvg
            max(0.0, min(100.0, 50.0 + (riskDelta * 100)))
        } ?: 50.0
        
        // Management Confidence
        val confidenceScore = sections.find { it.name == "md_a" }?.let { mdSection ->
            sentiments[mdSection]?.let { sentiment ->
                ((sentiment.positive - sentiment.negative + 1) * 50).toInt().toDouble()
            }
        } ?: 50.0
        
        // Event Velocity (simplified)
        val eventScore = if (historicalFilings.any { it.formType == "8-K" }) 70.0 else 30.0
        
        return SentimentSignals(
            riskAcceleration = riskScore,
            managementConfidence = confidenceScore,
            eventVelocity = eventScore
        )
    }
    
    private fun calculateOverallScore(
        sentiments: Map<FilingSection, FinBertResponse>,
        signals: SentimentSignals
    ): Double {
        val signalAvg = (signals.riskAcceleration * 0.3 +
                        signals.managementConfidence * 0.4 +
                        signals.eventVelocity * 0.3)
        
        val sentimentScores = sentiments.values.map { sentiment ->
            (sentiment.positive - sentiment.negative + 1) * 50
        }
        
        val sectionAvg = if (sentimentScores.isNotEmpty()) {
            sentimentScores.average()
        } else 50.0
        
        return max(0.0, min(100.0, signalAvg * 0.6 + sectionAvg * 0.4))
    }
    
    private fun calculateConfidence(sentiments: Map<FilingSection, FinBertResponse>): String {
        val avgNeutral = sentiments.values.map { it.neutral }.average()
        return when {
            avgNeutral < 0.3 -> "High"
            avgNeutral < 0.6 -> "Medium"
            else -> "Low"
        }
    }
    
    private fun determineTimeHorizon(
        sections: List<FilingSection>,
        signals: SentimentSignals
    ): TimeHorizon {
        var shortTerm = 50
        var longTerm = 50
        
        // Adjust based on signals
        if (signals.eventVelocity > 60) shortTerm += 20
        if (signals.riskAcceleration < 40) longTerm += 15
        
        return TimeHorizon(
            shortTerm = min(100, shortTerm),
            longTerm = min(100, longTerm)
        )
    }
    
    private fun extractKeyInsights(
        sections: List<FilingSection>,
        sentiments: Map<FilingSection, FinBertResponse>
    ): List<String> {
        val insights = mutableListOf<String>()
        
        sections.forEach { section ->
            sentiments[section]?.let { sentiment ->
                when {
                    section.name == "risk_factors" && sentiment.negative > 0.6 ->
                        insights.add("Elevated risk factors identified in recent filing")
                    
                    section.name == "md_a" && sentiment.positive > 0.6 ->
                        insights.add("Management expresses strong confidence in outlook")
                    
                    section.name == "md_a" && sentiment.negative > 0.6 ->
                        insights.add("Management tone suggests caution ahead")
                    
                    section.name == "forward_looking" && section.content.contains("growth", ignoreCase = true) ->
                        insights.add("Growth initiatives highlighted in forward guidance")
                }
            }
        }
        
        return insights.take(5)
    }
}
```

### 6. Main Service Implementation

```kotlin
// service/SecFilingsService.kt
package app.stocklens.secfilings.service

import app.stocklens.secfilings.config.FilingConfig
import app.stocklens.secfilings.dto.SecFilingsInsightsDto
import app.stocklens.secfilings.service.sec.SECAnalyzer
import app.stocklens.secfilings.service.sedar.SEDARAnalyzer
import kotlinx.coroutines.coroutineScope
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class SecFilingsService(
    private val secAnalyzer: SECAnalyzer,
    private val sedarAnalyzer: SEDARAnalyzer,
    private val sentimentAnalysisService: SentimentAnalysisService,
    private val config: FilingConfig
) {
    
    @Cacheable(value = ["sec-filings"], key = "#symbol + '-' + #exchange + '-' + #date")
    suspend fun getSecFilingsInsights(
        symbol: String,
        exchange: String,
        date: LocalDate
    ): SecFilingsInsightsDto = coroutineScope {
        
        // 1. Determine market and analyzer
        val market = if (exchange in listOf("NYSE", "NASDAQ")) "US" else "CA"
        val analyzer = if (market == "US") secAnalyzer else sedarAnalyzer
        
        // 2. Check cache
        val cacheKey = "sentiment:$market:$symbol:${LocalDate.now()}"
        analyzer.getCachedSentiment(cacheKey)?.let { return@coroutineScope it }
        
        // 3. Determine filing types
        val filingTypes = if (market == "US") {
            listOf("10-K", "10-Q", "8-K")
        } else {
            listOf("AIF", "MD&A", "Material Change Report")
        }
        
        // 4. Fetch recent filings
        val recentFilings = analyzer.fetchFilings(symbol, filingTypes)
        
        if (recentFilings.isEmpty()) {
            return@coroutineScope SecFilingsInsightsDto(
                ticker = symbol,
                market = market,
                sentimentScore = 50.0,
                confidence = "Low",
                keyInsights = listOf("No recent filings found")
            )
        }
        
        // 5. Get most recent filing content
        val latestFiling = recentFilings.first()
        val content = analyzer.getFilingContent(latestFiling)
        
        // 6. Extract sections
        val sections = analyzer.extractSections(content)
        
        // 7. Analyze sentiment
        val insights = sentimentAnalysisService.analyzeFilings(
            ticker = symbol,
            market = market,
            sections = sections,
            historicalFilings = recentFilings.drop(1).take(5)
        )
        
        // 8. Cache result
        val ttl = config.cache.ttl[latestFiling.formType] ?: config.cache.ttl["8-K"]!!
        analyzer.cacheSentiment(cacheKey, insights, ttl)
        
        insights
    }
}
```

### 7. Controller Implementation

```kotlin
// controller/SecFilingsController.kt
package app.stocklens.secfilings.controller

import app.stocklens.secfilings.dto.SecFilingsInsightsDto
import app.stocklens.secfilings.service.SecFilingsService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/sentiment")
class SecFilingsController(
    private val secFilingsService: SecFilingsService
) {
    
    @GetMapping("/analyze")
    suspend fun analyzeSentiment(
        @RequestParam symbol: String,
        @RequestParam exchange: String,
        @RequestParam(required = false) date: LocalDate?
    ): SecFilingsInsightsDto {
        return secFilingsService.getSecFilingsInsights(
            symbol = symbol,
            exchange = exchange,
            date = date ?: LocalDate.now()
        )
    }
}
```

### 8. Batch Caching Job

```kotlin
// job/FilingsCacheWarmer.kt
package app.stocklens.secfilings.job

import app.stocklens.secfilings.service.SecFilingsService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class FilingsCacheWarmer(
    private val secFilingsService: SecFilingsService
) {
    
    private val popularStocks = mapOf(
        "US" to listOf(
            "AAPL" to "NASDAQ",
            "MSFT" to "NASDAQ",
            "GOOGL" to "NASDAQ",
            "AMZN" to "NASDAQ",
            "TSLA" to "NASDAQ"
        ),
        "CA" to listOf(
            "SHOP" to "TSX",
            "RY" to "TSX",
            "TD" to "TSX",
            "CNR" to "TSX",
            "CP" to "TSX"
        )
    )
    
    @Scheduled(cron = "0 0 2 * * ?") // 2 AM daily
    fun warmCache() = runBlocking {
        coroutineScope {
            popularStocks.flatMap { (_, stocks) ->
                stocks.map { (symbol, exchange) ->
                    async {
                        try {
                            secFilingsService.getSecFilingsInsights(
                                symbol = symbol,
                                exchange = exchange,
                                date = LocalDate.now()
                            )
                            println("Cached $symbol")
                        } catch (e: Exception) {
                            println("Failed to cache $symbol: ${e.message}")
                        }
                    }
                }
            }.awaitAll()
        }
    }
}
```

### 9. Configuration Classes

```kotlin
// config/WebClientConfig.kt
package app.stocklens.secfilings.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {
    
    @Bean
    fun webClient(): WebClient {
        val connectionProvider = ConnectionProvider.builder("custom")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(20))
            .maxLifeTime(Duration.ofSeconds(60))
            .pendingAcquireTimeout(Duration.ofSeconds(60))
            .evictInBackground(Duration.ofSeconds(120))
            .build()
        
        val httpClient = HttpClient.create(connectionProvider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
            .responseTimeout(Duration.ofSeconds(30))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(30, TimeUnit.SECONDS))
                    .addHandlerLast(WriteTimeoutHandler(30, TimeUnit.SECONDS))
            }
        
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
}

// config/RedisConfig.kt
package app.stocklens.secfilings.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    
    @Bean
    fun reactiveRedisTemplate(
        connectionFactory: ReactiveRedisConnectionFactory
    ): ReactiveRedisTemplate<String, String> {
        val context = RedisSerializationContext
            .newSerializationContext<String, String>(StringRedisSerializer())
            .build()
        
        return ReactiveRedisTemplate(connectionFactory, context)
    }
}
```

### 10. Application Properties

```yaml
# application.yml
stocklens:
  filings:
    sec:
      base-url: https://data.sec.gov
      user-agent: "StockLens support@stocklens.com"
      rate-limit: 10
    sedar:
      base-url: https://www.sedarplus.ca
      scrape-delay: PT2S
    cache:
      ttl:
        10-K: P30D
        10-Q: P7D
        8-K: P1D
        AIF: P30D
        MD&A: P7D
        Material Change: P1D
    finbert:
      prosusai-url: ${FINBERT_PROSUSAI_URL:http://localhost:8081/analyze}
      yiyanghkust-url: ${FINBERT_YIYANGHKUST_URL:http://localhost:8082/analyze}

spring:
  cache:
    type: redis
    redis:
      time-to-live: 3600000
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
```

## Day 1 Implementation Plan

### Morning (4 hours)
1. Set up project structure and configuration classes
2. Implement SEC EDGAR data fetcher using WebClient
3. Test SEC API integration with rate limiting
4. Create models and DTOs

### Afternoon (4 hours)
5. Implement section extraction using JSoup
6. Integrate FinBERT endpoints with WebClient
7. Create sentiment scoring algorithm
8. Test end-to-end for one US stock

## Day 2 Implementation Plan

### Morning (4 hours)
1. Implement SEDAR web scraping with JSoup
2. Adapt section extraction for Canadian filings
3. Implement Redis caching layer

### Afternoon (4 hours)
4. Create the three signals calculation
5. Implement time horizon differentiation
6. Add scheduled cache warming job
7. Final testing and optimization

## Key Design Decisions

1. **Coroutine-Based**: All I/O operations use suspend functions
2. **WebClient**: Reactive HTTP client for external API calls
3. **Redis Caching**: Reactive Redis for high-performance caching
4. **JSoup**: HTML parsing for both SEC and SEDAR content
5. **Spring Cache**: Additional caching layer with annotations

## Performance Targets

- Response time: <3 seconds for cached, <15 seconds for fresh analysis
- Cache hit rate: >80% during market hours
- Concurrent requests: 50+ simultaneous analyses
- Daily capacity: 10,000+ unique stock analyses

This implementation leverages Kotlin's coroutines and Spring Boot's reactive stack for optimal performance and scalability.