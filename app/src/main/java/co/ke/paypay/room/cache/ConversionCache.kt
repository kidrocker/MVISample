package co.ke.paypay.room.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Sample entity class
 */
@Entity(tableName = "conversion")
data class ConversionCache(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,

    @ColumnInfo(name = "source")
    var source: String = "",

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0,

    @ColumnInfo(name = "created_at")
    var createdAt:Long=0,

    // value will be converted into an entity later
    // for now we hold it as a string
    @Ignore
    var quote: HashMap<String, Double>? = null
)

