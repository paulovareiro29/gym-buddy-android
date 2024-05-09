package ipvc.gymbuddy.app.core

import android.content.Context
import android.widget.EditText
import ipvc.gymbuddy.app.R

object Validator {
    fun isEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailRegex.toRegex())
    }

    fun validateRequiredField(field: EditText, context: Context): Boolean {
        val text = field.text.toString()

        if (text.isBlank()) {
            field.error = context.getString(R.string.field_is_required)
            return false
        }

        return true
    }

    fun validateEmailField(field: EditText, context: Context): Boolean {
        val email = field.text.toString()

        if (!isEmail(email)) {
            field.error = context.getString(R.string.invalid_email_format)
            return false
        }

        return true
    }

    fun validatePasswordField(field: EditText, context: Context): Boolean {
        val password = field.text.toString()
        val minimumLength = 8

        if (password.length < minimumLength) {
            field.error = context.getString(R.string.minimum_x_characters, minimumLength)
            return false
        }

        return true
    }
}