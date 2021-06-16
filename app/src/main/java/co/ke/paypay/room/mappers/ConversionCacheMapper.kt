package co.ke.paypay.room.mappers

import co.ke.paypay.models.Conversion
import co.ke.paypay.models.Quote
import co.ke.paypay.room.cache.ConversionCache
import co.ke.paypay.room.cache.ConversionWithQuote
import co.ke.paypay.room.cache.QuoteCache
import co.ke.paypay.utils.DomainEntityMapper
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Mapping domain model cache version
 */
class ConversionCacheMapper
@Inject
constructor() : DomainEntityMapper<ConversionWithQuote, Conversion> {

    override fun mapToDomain(entity: ConversionWithQuote): Conversion {
        return Conversion(
            source = entity.conversionCache.source,
            timestamp = entity.conversionCache.timestamp,
            quote = quoteCacheToQuote(entity.quoteCache)
        )
    }

    override fun mapToEntity(domainModel: Conversion): ConversionWithQuote {

        val conversion = ConversionCache(
            id = null,
            source = domainModel.source,
            timestamp = domainModel.timestamp,
            createdAt = Date().time
        )
        return ConversionWithQuote( conversionCache = conversion)
    }

    private fun quoteCacheToQuote(quoteCache: List<QuoteCache>): List<Quote> {
        val quotes: MutableList<Quote> = ArrayList()

        quoteCache.forEach { quote ->
            quotes.add(
                Quote(
                    code = quote.code, rateValue = quote.rateValue
                )
            )
        }

        return quotes
    }
}