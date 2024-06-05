package ipvc.gymbuddy.app.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    fun parseToUTC(date: Date): String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.format(date)
    }

    fun formatDateFromIso8601(dateStr: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return try {
            val date = parser.parse(dateStr)
            if (date != null) {
                formatter.format(date)
            } else {
                "Invalid date"
            }
        } catch (e: Exception) {
            "Invalid date"
        }
    }

}