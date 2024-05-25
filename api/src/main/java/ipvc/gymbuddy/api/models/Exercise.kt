package ipvc.gymbuddy.api.models


data class Exercise (
    val id: String,
    val name: String,
    val photo: String,
    val machine: Machine,
    val categories: List<Category>
)
