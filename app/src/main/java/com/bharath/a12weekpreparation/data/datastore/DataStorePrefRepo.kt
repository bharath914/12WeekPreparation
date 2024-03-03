package com.bharath.a12weekpreparation.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore by preferencesDataStore("Cache")

class DataStorePrefRepo @Inject constructor(
    @ApplicationContext context: Context,
) {
    private object PrefKeys {
        val tabIndex = intPreferencesKey("TabIndex")
    }

    private val dataStore = context.dataStore
    suspend fun setTabIndex(index: Int) {
        dataStore.edit {
            it[PrefKeys.tabIndex] = index
        }
    }

    fun getTabIndex(): Flow<Int> {
        return dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            val res = it[PrefKeys.tabIndex] ?: 0
            res
        }
    }
}