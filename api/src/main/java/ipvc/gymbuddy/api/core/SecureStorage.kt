package ipvc.gymbuddy.api.core

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson

class SecureStorage(sharedPreferencesName: String, context: Context) {
    private var masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        context,
        sharedPreferencesName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val gson = Gson()

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun setString(key: String, value: String): Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }

    fun <T> getObject(key: String, type: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return json?.let {
            gson.fromJson(it, type)
        }
    }

    fun setObject(key: String, value: Any): Boolean {
        val json = gson.toJson(value)
        return sharedPreferences.edit().putString(key, json).commit()
    }
}