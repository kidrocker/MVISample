package co.ke.paypay.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.ke.paypay.room.cache.ConversionCache
import co.ke.paypay.room.cache.QuoteCache
import co.ke.paypay.room.dao.ConversionDAO

@Database(
    entities = [ConversionCache::class, QuoteCache::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun conversionDao(): ConversionDAO

    companion object {
        val DATABASE_NAME: String = "pay_db"
    }

}