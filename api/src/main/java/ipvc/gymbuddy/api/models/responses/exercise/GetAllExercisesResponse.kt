package ipvc.gymbuddy.api.models.responses.exercise

import ipvc.gymbuddy.api.models.Exercise

data class GetAllExercisesResponse (
    val exercises: List<Exercise>
)