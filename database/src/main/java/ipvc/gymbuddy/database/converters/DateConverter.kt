package ipvc.gymbuddy.database.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateConverter {

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        if (date == null) return null
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.format(date)
    }

    @TypeConverter
    fun fromStringToDate(dateString: String?): Date? {
        if (dateString == null) return null
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            parser.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}
