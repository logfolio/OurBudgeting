package com.fintern.ourbudgeting.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object PreferencesKeys {
        val UID = stringPreferencesKey("uid")
        val NICKNAME = stringPreferencesKey("nickname")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun updateUser(uid: String, nickname: String, email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UID] = uid
            preferences[PreferencesKeys.NICKNAME] = nickname
            preferences[PreferencesKeys.EMAIL] = email
        }
    }

    fun readUser(): Flow<UserPreferences> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val uid = preferences[PreferencesKeys.UID] ?: ""
                val nickname = preferences[PreferencesKeys.NICKNAME] ?: ""
                val email = preferences[PreferencesKeys.EMAIL] ?: ""
                UserPreferences(uid, nickname, email)
            }
    }
}

data class UserPreferences(val uid: String = "", val nickname: String = "", val email: String = "")