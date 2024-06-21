package ipvc.gymbuddy.api.models.requests.planExercise

data class UpdatePlanExerciseRequest(
    var exercise_id: String,
    var repetitions: Int,
    var sets: Int,
    var rest_between_sets: Int,
    var day: String
)