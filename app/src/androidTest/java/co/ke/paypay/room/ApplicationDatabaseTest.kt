package co.ke.paypay.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.ke.paypay.room.dao.ConversionDAO
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseTest : TestCase(){

    private lateinit var conversionDAO: ConversionDAO
    private lateinit var db:ApplicationDatabase


    @Before
    public override fun setUp() {
//        val context = ApplicationProvider.getApplicationContext()
//        db = Room.inMemoryDatabaseBuilder(
//            context, ApplicationDatabase::class.java
//        ).build()
//        conversionDAO = db.conversionDao()
    }

    @Test
    fun writeAndReadConversions()= runBlocking {

    }

}