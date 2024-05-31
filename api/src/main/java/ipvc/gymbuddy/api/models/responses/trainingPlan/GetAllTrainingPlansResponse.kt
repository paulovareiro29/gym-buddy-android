package ipvc.gymbuddy.api.models.responses.trainingPlan

import ipvc.gymbuddy.api.models.TrainingPlan

data class GetAllTrainingPlansResponse (
    val trainingPlans: List<TrainingPlan>
)

