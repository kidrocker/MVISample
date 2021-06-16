package co.ke.paypay.room.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteCache(
    @PrimaryKey(autoGenerate = false)
    var id: Int?=0,

    @ColumnInfo(name = "code")
    var code: String="",

    @ColumnInfo(name = "value")
    var rateValue: Double=0.0,

    @ColumnInfo(name = "conversion_id")
    var conversionID: Long=0
)