package ipvc.gymbuddy.app.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object DateUtils {

    fun parseToUTC(date: Date): String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.format(date)
    }

    @SuppressLint("NewApi")
    fun formatDateFromIso8601(date: Date): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        return formatter.format(localDate)
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        calendar1.time = date1

        val calendar2 = Calendar.getInstance()
        calendar2.time = date2

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

}