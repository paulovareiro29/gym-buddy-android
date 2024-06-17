package ipvc.gymbuddy.app.core

import android.annotation.SuppressLint
import android.content.Context
import ipvc.gymbuddy.api.core.SecureStorage

class LanguageStorage {
    lateinit var context: Context
    lateinit var secureStorage: SecureStorage

    val LANGUAGE_KEY = "CURRENT_LANGUAGE"

    @SuppressLint("StaticFieldLeak")
    companion object {
        @Volatile private var instance: LanguageStorage? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: LanguageStorage().also { instance = it }
            }
    }

    fun init(context: Context) {
        this.context = context
        secureStorage = SecureStorage("LANGUAGE_STORAGE", context)
    }

    fun getCurrentLanguage(): String? {
        if (!this::secureStorage.isInitialized) throw Error("Secure Storage has not been initialized")
        return secureStorage.getString(LANGUAGE_KEY)
    }

    fun setCurrentLanguage(language: String?): Boolean {
        if (!this::secureStorage.isInitialized) throw Error("Secure Storage has not been initialized")
        return secureStorage.setString(LANGUAGE_KEY, language)
    }
}