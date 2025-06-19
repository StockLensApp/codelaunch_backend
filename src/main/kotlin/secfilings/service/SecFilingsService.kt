package app.stocklens.secfilings.service

import app.stocklens.secfilings.dto.SecFilingsInsightsDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SecFilingsService {

    suspend fun getSecFilingsInsights(symbol: String, exchange: String, date: LocalDate): SecFilingsInsightsDto {

        return SecFilingsInsightsDto("","", 0.0, "")
    }
}