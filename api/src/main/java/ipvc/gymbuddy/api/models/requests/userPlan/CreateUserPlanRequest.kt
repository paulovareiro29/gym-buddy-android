package ipvc.gymbuddy.api.models.requests.userPlan

import java.util.Date

data class CreateUserPlanRequest(
    val user_id: String,
    val plan_id: String,
    val start_date: Date,
    val end_date: Date
)
