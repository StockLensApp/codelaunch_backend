package app.stocklens.domain.enums

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
enum class TimeHorizon(
    val term: String,
    val displayName: String,
    val description: String,
    @Suppress("unused") val barInterval: String,
    val incrementFrequency: Duration,
    val maxQuarters: Int,
    val monthsOfPricingData: Int,
) {
    SHORT_TERM(
        term = "short-term",
        displayName = "Short-Term",
        description = "Suitable for investments typically held within a 1-2 week span.",
        barInterval = "Every 1 hour",
        incrementFrequency = Duration.ofHours(1),
        maxQuarters = 15,
        monthsOfPricingData = 2,
    ),
    MEDIUM_TERM(
        term = "medium-term",
        displayName = "Medium-Term",
        description = "Suitable for investments typically held within a 4-8 week span.",
        barInterval = "Daily close",
        incrementFrequency = Duration.ofDays(1),
        maxQuarters = 15,
        monthsOfPricingData = 5,
    ),
    LONG_TERM(
        term = "long-term",
        displayName = "Long-Term",
        description = "Intended for investments held for greater than 2 months to years.",
        barInterval = "Weekly close",
        incrementFrequency = Duration.ofDays(7),
        maxQuarters = 15,
        monthsOfPricingData = 36,
    ),
    UNDEFINED(
        term = "undefined",
        displayName = "Undefined",
        description = "Time Horizon undefined",
        barInterval = "N/A",
        incrementFrequency = Duration.ZERO,
        maxQuarters = 0,
        monthsOfPricingData = 0,
    ),
}
