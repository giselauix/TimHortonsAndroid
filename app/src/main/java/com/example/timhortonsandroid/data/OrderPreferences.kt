package com.example.timhortonsandroid.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore used to persist favorite coffee orders.
 *
 * Favorite orders are stored locally so they remain available
 * after the application has been closed and reopened.
 */
private val Context.dataStore by preferencesDataStore(
    name = "coffee_run_preferences"
)

class OrderPreferences(
    private val context: Context
) {

    companion object {

        /**
         * Key used to store favorite orders.
         */
        private val FAVORITE_ORDERS:
                Preferences.Key<Set<String>> =
            stringSetPreferencesKey("favorite_orders")
    }

    /**
     * Provides the saved favorite orders as a Flow.
     */
    val favoriteOrders: Flow<Set<String>> =
        context.dataStore.data.map { preferences ->

            preferences[FAVORITE_ORDERS]
                ?: emptySet()
        }

    /**
     * Saves a favorite order to DataStore.
     */
    suspend fun saveFavoriteOrder(
        order: String
    ) {

        context.dataStore.edit { preferences ->

            val currentOrders =
                preferences[FAVORITE_ORDERS]
                    ?: emptySet()

            preferences[FAVORITE_ORDERS] =
                currentOrders + order
        }
    }

    /**
     * Removes a favorite order from DataStore.
     */
    suspend fun removeFavoriteOrder(
        order: String
    ) {

        context.dataStore.edit { preferences ->

            val currentOrders =
                preferences[FAVORITE_ORDERS]
                    ?: emptySet()

            preferences[FAVORITE_ORDERS] =
                currentOrders - order
        }
    }
}