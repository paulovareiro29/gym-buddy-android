package ipvc.gymbuddy.api.models.responses.contract

import ipvc.gymbuddy.api.models.Contract

data class GetAllContractsResponse (
    val contracts: List<Contract>
)