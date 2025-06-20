package app.stocklens.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Sector(
    val sectorName: String,
) {
    BASIC_MATERIALS("Basic Materials"),
    COMMUNICATION_SERVICES("Communication Services"),
    CONSUMER_CYCLICAL("Consumer Cyclical"),
    CONSUMER_DEFENSIVE("Consumer Defensive"),
    ENERGY("Energy"),
    FINANCIAL_SERVICES("Financial Services"),
    HEALTHCARE("Healthcare"),
    INDUSTRIALS("Industrials"),
    REAL_ESTATE("Real Estate"),
    TECHNOLOGY("Technology"),
    UTILITIES("Utilities"),
}
