package ipvc.gymbuddy.api.models.requests.metric

data class UpdateMetricRequest(
    var type_id : String,
    var value : String
)