package ipvc.gymbuddy.api.models.responses.category

import ipvc.gymbuddy.api.models.Category


data class GetAllCategoriesResponse (
    val categories: List<Category>
)