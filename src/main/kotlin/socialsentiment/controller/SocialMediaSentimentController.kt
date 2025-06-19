package app.stocklens.socialsentiment.controller

import app.stocklens.socialsentiment.dto.SocialMediaInsightDto
import app.stocklens.socialsentiment.service.SocialMediaSentimentService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/social-media")
class SocialMediaSentimentController(
    private val socialMediaSentimentService: SocialMediaSentimentService,
) {

    @GetMapping("/insights")
    suspend fun getSocialMediaInsights(
        @RequestParam symbol: String,
        @RequestParam exchange: String,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
    ): ResponseEntity<SocialMediaInsightDto> {
        val socialMediaInsights = socialMediaSentimentService.getSocialMediaInsights(symbol, exchange, date ?: LocalDate.now())
        return ResponseEntity.ok(socialMediaInsights)
    }
}