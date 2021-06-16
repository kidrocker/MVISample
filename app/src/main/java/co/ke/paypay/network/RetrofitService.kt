package co.ke.paypay.network

import co.ke.paypay.network.entities.ConversionEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("live")
    suspend fun convertOnline(
            @Query("access_key") access_key: String,
    ): ConversionEntity
}