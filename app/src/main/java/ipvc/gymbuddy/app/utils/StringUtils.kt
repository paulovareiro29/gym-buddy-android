package ipvc.gymbuddy.app.utils

object StringUtils {

    fun capitalize(value: String?): String? {
        if (value.isNullOrEmpty()) {
            return value
        }
        return value.replaceFirstChar { it.uppercaseChar() }
    }
}