package ipvc.gymbuddy.api.models.requests.machine

data class UpdateMachineRequest(
    var name: String,
    var photo: String?,
    var categories: List<String>
)
