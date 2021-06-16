package co.ke.paypay.models

data class Conversion(
    val source: String,
    val timestamp: Long,
    val quote: List<Quote>
)
