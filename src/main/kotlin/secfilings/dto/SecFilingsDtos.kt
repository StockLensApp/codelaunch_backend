package app.stocklens.secfilings.dto

data class SecFilingsInsightsDto(
    val filingId: String,
    val filingType: String,
    val score: Double,
    val rationale: String,
)