import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Genres (
		@PrimaryKey
		@SerializedName("id") val id : Int,

		@SerializedName("name") val name : String
)