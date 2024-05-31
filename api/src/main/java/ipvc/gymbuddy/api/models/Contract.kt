package ipvc.gymbuddy.api.models

data class Contract (
    val id: String,
    val beneficiary: SimplifiedUser,
    val provider: SimplifiedUser,
    val category: Category,
    val start_date: String,
    val end_date: String
)
