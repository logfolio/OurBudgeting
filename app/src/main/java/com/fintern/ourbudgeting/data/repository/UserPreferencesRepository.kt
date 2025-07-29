package com.fintern.ourbudgeting.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object PreferencesKeys {
        val UID = stringPreferencesKey("uid")
        val NICKNAME = stringPreferencesKey("nickname")
    }

    suspend fun updateUser(uid: String, nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UID] = uid
            preferences[PreferencesKeys.NICKNAME] = nickname
        }
    }
}

data class UserPreferences(val uid: String = "", val nickname: String = "")