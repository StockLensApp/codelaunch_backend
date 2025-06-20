package app.stocklens.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TimeRange(
    val timeHorizon: TimeHorizon,
    val range: String,
    val simpleRange: String,
) {
    SHORT_RANGE_1_2(
        TimeHorizon.SHORT_TERM,
        "1 - 2 weeks",
        "<2 wks",
    ),
    MEDIUM_RANGE_3_6(
        TimeHorizon.MEDIUM_TERM,
        "3 - 6 weeks",
        "~1 mo",
    ),
    MEDIUM_RANGE_5_10(
        TimeHorizon.MEDIUM_TERM,
        "5 - 10 weeks",
        "~2 mo",
    ),
    LONG_RANGE_0_1(
        TimeHorizon.LONG_TERM,
        "3m - 1 year",
        "~1 yr",
    ),
    LONG_RANGE_1_2(
        TimeHorizon.LONG_TERM,
        "1 - 2 years",
        "~2 yrs",
    ),
    UNDEFINED(
        TimeHorizon.UNDEFINED,
        "N/A",
        "N/A",
    ),
}
