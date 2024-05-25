package ipvc.gymbuddy.api.models

data class TrainingPlan (
    val id: String,
    val name: String,
    val creator: SimplifiedUser
)