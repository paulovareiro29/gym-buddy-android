package ipvc.gymbuddy.api.models.requests.contract

data class CreateContractRequest(
    val beneficiary_id: String,
    val provider_id: String,
    val category_id: String,
    val start_date: String,
    val end_date: String
)
