package com.example.cinemaapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferences(private val context: Context) {

    val lastCinema: Flow<String> = context.userPreferencesDataStore.data.map { prefs ->
        prefs[LAST_CINEMA_KEY] ?: DEFAULT_CINEMA
    }

    val lastRoute: Flow<String> = context.userPreferencesDataStore.data.map { prefs ->
        prefs[LAST_ROUTE_KEY] ?: DEFAULT_ROUTE
    }

    suspend fun saveLastCinema(cinema: String) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[LAST_CINEMA_KEY] = cinema
        }
    }

    suspend fun saveLastRoute(route: String) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[LAST_ROUTE_KEY] = route
        }
    }

    companion object {
        const val DEFAULT_CINEMA = "Odeon"
        const val DEFAULT_ROUTE = "now_playing"
        private val LAST_CINEMA_KEY = stringPreferencesKey("last_cinema")
        private val LAST_ROUTE_KEY = stringPreferencesKey("last_route")
    }
}

