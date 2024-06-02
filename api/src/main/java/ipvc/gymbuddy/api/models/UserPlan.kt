package ipvc.gymbuddy.api.models

import java.util.Date

data class UserPlan (
    val user: SimplifiedUser,
    val plan : TrainingPlan,
    val start_date: Date,
    val end_date: Date
)
