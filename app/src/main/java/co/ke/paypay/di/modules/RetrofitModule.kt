package co.ke.paypay.di.modules

import android.app.Activity
import android.content.Context
import co.ke.paypay.network.RetrofitService
import co.ke.paypay.utils.GlobalUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Inject retrofit
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(GlobalUtils.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit.Builder): RetrofitService {
        return retrofit
            .build()
            .create(RetrofitService::class.java)
    }


        @Singleton
    @Provides
    fun provideStaticHeaders(
            @ApplicationContext activity: Activity,
            retrofit: Retrofit.Builder
    ): Retrofit.Builder {

        // Allow retrofit to work on devices lower than android 5
        val lists = listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.MODERN_TLS)
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)

        val httpClient = OkHttpClient.Builder()
            .callTimeout(2, java.util.concurrent.TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .connectionSpecs(lists)
            .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${sharedPref.getString("token", "qwertyuiop")}")
                .build()
            chain.proceed(request)
        }
        return retrofit.client(httpClient.build())
    }
}