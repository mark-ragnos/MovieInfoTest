import com.google.gson.annotations.SerializedName


data class MovieDetails(


    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("genres") val genres: List<Genres>,
    val poster_path: String
)