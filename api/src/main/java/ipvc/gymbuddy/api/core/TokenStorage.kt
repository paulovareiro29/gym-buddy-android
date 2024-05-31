package ipvc.gymbuddy.api.core

import android.annotation.SuppressLint
import android.content.Context

class TokenStorage {
    lateinit var context: Context
    lateinit var secureStorage: SecureStorage

    private val TOKEN_KEY = "API_TOKEN_KEY"

    @SuppressLint("StaticFieldLeak")
    companion object {
        @Volatile private var instance: TokenStorage? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TokenStorage().also { instance = it }
            }
    }

    fun init(context: Context) {
        this.context = context
        secureStorage = SecureStorage("TOKEN_STORAGE", context)
    }

    fun setToken(token: String?): Boolean {
        if (!this::secureStorage.isInitialized) throw Error("Secure Storage has not been initialized")
        return secureStorage.setString(TOKEN_KEY, token)
    }

    fun getToken(): String? {
        if (!this::secureStorage.isInitialized) throw Error("Secure Storage has not been initialized")
        return secureStorage.getString(TOKEN_KEY)
    }
}