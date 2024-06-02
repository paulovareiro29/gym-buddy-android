package ipvc.gymbuddy.api.models

data class PlanExercise(
    var id : String,
    var plan : TrainingPlan,
    var exercise: Exercise,
    var repetitions : Int,
    var sets: Int,
    var rest_between_sets: Int,
    var day: Int
)