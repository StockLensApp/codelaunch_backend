package app.stocklens.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class RiskTolerance(
    val displayName: String,
) {
    LOW(
        displayName = "Low (Income)",
    ),
    MEDIUM(
        displayName = "Medium (Balanced)",
    ),
    HIGH(
        displayName = "High (Growth)",
    ),
}
