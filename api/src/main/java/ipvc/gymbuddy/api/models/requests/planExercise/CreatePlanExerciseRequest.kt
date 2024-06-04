package ipvc.gymbuddy.api.models.requests.planExercise

data class CreatePlanExerciseRequest(
    var exercise_id: String,
    var repetitions: Int,
    var sets: Int,
    var rest_between_sets: Int,
    var day: Int
)