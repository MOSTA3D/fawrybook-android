package com.mosta3d.fawrybook.auth.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecretsStore(context: Context) {
    private var preferences: SharedPreferences

    init {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        preferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setItem(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getItem(key: String): String? {
        return preferences.getString(key, null)
    }
}