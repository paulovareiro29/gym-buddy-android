package ipvc.gymbuddy.api.models.requests.metric

data class CreateMetricRequest(
    var user_id : String,
    var creator_id : String,
    var type_id : String,
    var value : String,
    var date : String
)