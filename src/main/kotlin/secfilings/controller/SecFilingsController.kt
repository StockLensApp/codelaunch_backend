package app.stocklens.secfilings.controller

import app.stocklens.secfilings.dto.SecFilingsInsightsDto
import app.stocklens.secfilings.service.SecFilingsService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/sec-filings")
class SecFilingsController(
    private val secFilingsService: SecFilingsService
) {

    @GetMapping("/insights")
    suspend fun getSecFilingsInsights(
        @RequestParam symbol: String,
        @RequestParam exchange: String,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate?,
    ): ResponseEntity<SecFilingsInsightsDto> {
        val socialMediaInsights = secFilingsService.getSecFilingsInsights(symbol, exchange, date ?: LocalDate.now())
        return ResponseEntity.ok(socialMediaInsights)
    }
}