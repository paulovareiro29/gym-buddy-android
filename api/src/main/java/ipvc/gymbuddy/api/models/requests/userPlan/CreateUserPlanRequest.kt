package ipvc.gymbuddy.api.models.requests.userPlan

data class CreateUserPlanRequest(
    val plan_id: String,
    val start_date: String,
    val end_date: String
)
