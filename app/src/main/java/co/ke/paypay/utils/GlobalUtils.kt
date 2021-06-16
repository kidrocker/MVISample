package co.ke.paypay.utils

import android.content.Context
import android.util.Log
import co.ke.paypay.R
import co.ke.paypay.models.Currency
import co.ke.paypay.room.cache.QuoteCache
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object GlobalUtils {

    // Method will return the url depending on the build type
    // Since this is test we will return
    fun getBaseUrl(): String {
        return "http://api.currencylayer.com/"
    }

    /**
     * Converts string json to List currency
     * @return List<Currency>
     */
    fun stringToCurrencyList(context: Context?): List<Currency> {
        val builder = GsonBuilder().create()
        val json = context?.resources?.getString(R.string.currencies)?.trim()
        return builder.fromJson(
                json,
                Array<Currency>::class.java)
                .toList()
    }
}
