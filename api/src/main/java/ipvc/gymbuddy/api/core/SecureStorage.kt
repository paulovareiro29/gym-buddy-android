package ipvc.gymbuddy.api.core

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

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
    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun setString(key: String, value: String): Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }
}