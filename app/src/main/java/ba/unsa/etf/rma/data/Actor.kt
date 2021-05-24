package ba.unsa.etf.rma.data

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("name") var name: String
) {
}