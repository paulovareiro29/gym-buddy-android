package ipvc.gymbuddy.api.models.responses.planExercise

import ipvc.gymbuddy.api.models.PlanExercise

data class GetAllPlanExercisesResponse(
    val planExercises: List<PlanExercise>
)