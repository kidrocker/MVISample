package co.ke.paypay.room.cache

import androidx.room.Embedded
import androidx.room.Relation

data class ConversionWithQuote(
    @Embedded val conversionCache: ConversionCache,
    @Relation(
        parentColumn = "id",
        entityColumn = "conversion_id"
    )
    val quoteCache: List<QuoteCache> = emptyList()
)
