package ba.unsa.etf.rma.data

import com.google.gson.annotations.SerializedName

data class GetCreditsResponse(
    @SerializedName("cast") val actors: List<Actor>
) {
}