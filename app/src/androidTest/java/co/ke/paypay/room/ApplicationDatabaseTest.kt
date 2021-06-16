package co.ke.paypay.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.ke.paypay.room.cache.ConversionCache
import co.ke.paypay.room.cache.QuoteCache
import co.ke.paypay.room.dao.ConversionDAO
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseTest : TestCase(){

    private lateinit var conversionDAO: ConversionDAO
    private lateinit var db: ApplicationDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java
        ).build()
        conversionDAO = db.conversionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadConversions() {
        runBlocking {
            val conversion = ConversionCache(
                id = null,
                source = "USD",
                timestamp = 1623723244,
                createdAt = Date().time
            )
            val id = conversionDAO.insert(conversion)

            val quote = QuoteCache(id = null, code = "KSH", rateValue = 12.5, conversionID = id)
            val quoteOne = QuoteCache(id = null, code = "UGX", rateValue = 101.0, conversionID = id)

            conversionDAO.insertQuotes(listOf(quote, quoteOne))

            val conversionWithQuote = conversionDAO.getConversion(id)

            assertEquals(listOf(quote, quoteOne).size, conversionWithQuote.quoteCache.size)
        }
    }
}