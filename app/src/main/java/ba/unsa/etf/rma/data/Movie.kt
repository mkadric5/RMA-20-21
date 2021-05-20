package ba.unsa.etf.rma.data

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("id") var id: Long,
    @SerializedName("title")  var title: String,
    @SerializedName("overview")  var overview: String,
    @SerializedName("release_date")   var releaseDate: String,
    @SerializedName("homepage")   var homepage: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("backdrop_path")  var backdropPath: String?
)