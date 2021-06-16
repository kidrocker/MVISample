package co.ke.paypay.network.entities;

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ConversionEntity(

 @SerializedName("success") val success : Boolean,
 @SerializedName("terms") val terms : String?,
 @SerializedName("privacy") val privacy : String?,
 @SerializedName("timestamp") val timestamp : Long,
 @SerializedName("source") val source : String,
 @SerializedName("quotes") val quotes:HashMap<String, Double>?

 ) : Serializable
