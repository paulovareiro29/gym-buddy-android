package ipvc.gymbuddy.api.models


data class Machine (
    val id: String,
    val name: String,
    val photo: String,
    val categories: List<Category>
)
