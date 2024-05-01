package ipvc.gymbuddy.app.core

import android.content.Context
import android.widget.EditText
import ipvc.gymbuddy.app.R

object Validator {
    fun isEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailRegex.toRegex())
    }

    fun validateEmailField(field: EditText, context: Context): Boolean {
        val email = field.text.toString()

        if (email.isBlank()) {
            field.error = context.getString(R.string.email_is_required)
            return false
        }

        return true
    }

    fun validatePasswordField(field: EditText, context: Context): Boolean {
        val text = field.text.toString()

        if (text.isBlank()) {
            field.error = context.getString(R.string.password_is_required)
            return false
        }

        return true
    }
}