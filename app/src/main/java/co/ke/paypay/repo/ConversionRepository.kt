package co.ke.paypay.repo

import co.ke.paypay.models.Conversion
import co.ke.paypay.models.Quote
import co.ke.paypay.network.RetrofitService
import co.ke.paypay.network.mappers.ConversionMapper
import co.ke.paypay.room.cache.QuoteCache
import co.ke.paypay.room.dao.ConversionDAO
import co.ke.paypay.room.mappers.ConversionCacheMapper
import co.ke.paypay.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ConversionRepository
@Inject
constructor(
    private val conversionDAO: ConversionDAO,
    private val retrofitService: RetrofitService,
    private val networkMapper: ConversionMapper,
    private val cacheMapper: ConversionCacheMapper
) {

    // TODO: should securely store later
    private val accessKey = "e30b5bdbc1835fb3d7f21c0bd34e2d8b"

    suspend fun getConversions(code: String, amount: Double): Flow<DataState<Conversion>> = flow {
        emit(DataState.Loading)
        try {

            // get conversion
            var conversionWithQuote = conversionDAO.conversions()

            // check is the quotes exists or time is more than 30 mins since last save
            if (conversionWithQuote == null || Date().time - conversionWithQuote.conversionCache.createdAt >= 30 * 60 * 1000) {

                // delete before value
                conversionDAO.deleteAll()
                conversionDAO.deleteAllQuotes()

                // get the values from network
                val convertOnline = retrofitService.convertOnline(accessKey)

                // convert to database object
                val conversion = networkMapper.mapToDomain(convertOnline)

                // save conversion to the database
                val id = conversionDAO.insert(conversion)

                // format quotes
                val quote: MutableList<QuoteCache> = ArrayList()
                conversion.quote?.mapTo(quote,
                    {
                        QuoteCache(null, it.key, it.value, id)
                    }
                )

                // save
                conversionDAO.insertQuotes(quote)

                // get conversion since new data is added
                conversionWithQuote = conversionDAO.conversions()
            }

            // map the data
            val converted = cacheMapper.mapToDomain(conversionWithQuote)

            converted.quote.forEach {
                it.rateValue =
                    convertCurrency(amount, codeToRate(code, converted.quote), it.rateValue)
            }

            // notify the ui of data via viewModel
            emit(DataState.Success(converted))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    /**
     * Converts to the amount to base currency
     */
    private fun convertCurrency(amount: Double, baseCurrency: Double, targetCurrency: Double) =
        (baseCurrency / targetCurrency) * amount


    /**
     * Returns the currency rate from code
     *
     */
    private fun codeToRate(code: String, rates: List<Quote>): Double {
        return  rates.first {
            val codex = it.code.substring(3)
            codex == code
        }.rateValue
    }
}