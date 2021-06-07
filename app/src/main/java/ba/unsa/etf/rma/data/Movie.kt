package ba.unsa.etf.rma.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie (
    @PrimaryKey @SerializedName("id") var id: Long,
    @ColumnInfo(name = "title") @SerializedName("title")  var title: String,
    @ColumnInfo(name = "overview") @SerializedName("overview")  var overview: String,
    @ColumnInfo(name = "release_date") @SerializedName("release_date")   var releaseDate: String,
    @ColumnInfo(name = "homepage") @SerializedName("homepage")   var homepage: String?,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") var posterPath: String?,
    @ColumnInfo(name = "backdrop_path") @SerializedName("backdrop_path")  var backdropPath: String?
)