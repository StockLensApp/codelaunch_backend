package app.stocklens.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class CompanySize(
    val displayName: String,
) {
    // Less than $500 Million
    SMALL_CAP("Small-Cap"),

    // $500 Million to <$10 Billion
    MEDIUM_CAP("Medium-Cap"),

    // $10 Billion to <$100 Billion
    LARGE_CAP("Large-Cap"),

    // Greater than $100 Billion
    MEGA_CAP("Very Large-Cap"),
}
