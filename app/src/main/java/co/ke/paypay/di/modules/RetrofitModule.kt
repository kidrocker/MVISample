package co.ke.paypay.di.modules

import co.ke.paypay.BuildConfig
import co.ke.paypay.network.RetrofitService
import co.ke.paypay.utils.GlobalUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
                .baseUrl(GlobalUtils.getBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit.Builder): RetrofitService {
        return retrofit
                .build()
                .create(RetrofitService::class.java)
    }

    /**
     * Logging for debug functions
     */
    private val logging = HttpLoggingInterceptor().setLevel(
            when (BuildConfig.BUILD_TYPE) {
                "release" -> HttpLoggingInterceptor.Level.NONE
                else -> HttpLoggingInterceptor.Level.BODY
            }
    )
}