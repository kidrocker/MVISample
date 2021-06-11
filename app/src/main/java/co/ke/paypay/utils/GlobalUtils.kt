package co.ke.paypay.utils

import android.os.Build
import java.util.*

object GlobalUtils {

    // Method will return the url depending on the build type
    // Since this is test we will return
    fun getBaseUrl(): String {
        return "https://api.currencylayer.com/"
        }
    }
}
