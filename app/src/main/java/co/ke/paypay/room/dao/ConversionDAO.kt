package co.ke.paypay.room.dao

import androidx.room.*
import co.ke.paypay.room.cache.ConversionCache
import co.ke.paypay.room.cache.ConversionWithQuote
import co.ke.paypay.room.cache.QuoteCache

@Dao
interface ConversionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(conversion: ConversionCache): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes:List<QuoteCache>)

    @Transaction
    @Query("SELECT * FROM conversion WHERE id=:id")
    suspend fun getConversion(id: Int): ConversionWithQuote

    @Transaction
    @Query("SELECT * FROM conversion LIMIT 1")
    suspend fun conversions():ConversionWithQuote

    @Query("DELETE FROM conversion")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM quotes")
    suspend fun deleteAllQuotes(): Int
}
