package ipvc.gymbuddy.api.models.responses.userPlan

import ipvc.gymbuddy.api.models.UserPlan

data class GetAllUserPlanResponse (
    val userPlans: List<UserPlan>
)