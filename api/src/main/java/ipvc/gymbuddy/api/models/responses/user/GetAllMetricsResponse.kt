package ipvc.gymbuddy.api.models.responses.user

data class GetAllMetricsResponse (
    val number_of_contracts: Int,
    val number_of_created_plans: Int,
    val number_of_associated_plans: Int,
    val number_of_metrics : Int
)