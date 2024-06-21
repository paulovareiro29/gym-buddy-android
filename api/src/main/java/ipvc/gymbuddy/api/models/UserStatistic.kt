package ipvc.gymbuddy.api.models

data class UserStatistic (
    val user_id: String,
    val number_of_contracts: Int,
    val number_of_created_plans: Int,
    val number_of_associated_plans: Int,
    val number_of_metrics : Int
)