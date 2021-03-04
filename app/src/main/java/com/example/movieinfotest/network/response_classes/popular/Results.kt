import com.google.gson.annotations.SerializedName


data class Results(

    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("popularity") val popularity: Double,

    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String
)