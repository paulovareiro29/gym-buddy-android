package ipvc.gymbuddy.api.models.requests.exercise

data class CreateExerciseRequest(
    var name: String,
    var photo: String?,
    var machine_id: String,
    var categories: List<String>
)
