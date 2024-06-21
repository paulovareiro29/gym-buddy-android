package ipvc.gymbuddy.api.models.responses.contractCategory

import ipvc.gymbuddy.api.models.ContractCategory

data class GetAllContractCategoriesResponse (
    val contractCategories: List<ContractCategory>
)