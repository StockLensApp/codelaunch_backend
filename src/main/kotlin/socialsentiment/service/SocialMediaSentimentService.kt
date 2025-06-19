package app.stocklens.socialsentiment.service

import app.stocklens.socialsentiment.dto.SocialMediaInsightDto
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SocialMediaSentimentService {

    suspend fun getSocialMediaInsights(symbol: String, exchange: String, date: LocalDate): SocialMediaInsightDto {

        return SocialMediaInsightDto(0.0, "")
    }
}