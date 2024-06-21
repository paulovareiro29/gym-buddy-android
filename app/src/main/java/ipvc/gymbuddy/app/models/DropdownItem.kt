package ipvc.gymbuddy.app.models


data class DropdownItem (
    val id: String,
    val name: String,
) {
    override fun toString(): String {
        return name
    }
}
