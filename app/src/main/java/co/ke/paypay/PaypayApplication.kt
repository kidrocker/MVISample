package co.ke.paypay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PaypayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}