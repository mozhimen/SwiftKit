package com.mozhimen.basicktest.cachek

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basicktest.databinding.ActivityCachekDsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CacheKDSActivity : BaseActivityVB<ActivityCachekDsBinding>() {

    // At the top level of your kotlin file:
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

    val exampleCounterFlow: Flow<Int> = this.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[EXAMPLE_COUNTER] ?: 0
        }

    override fun initView(savedInstanceState: Bundle?) {

    }

//    val userPreferencesFlow: Flow<MResultBS> = dataStore.data
//        .catch { exception ->
//            // datastore.data throws an IOException when an error is encountered when reading data
//            if (exception is IOException) {
//                Log.e(TAG，" Error reading preferences.", exception)
//                emit(emptyPreferences())
//            } else {
//                throw exception
//            }
//        }.map { preferences ->
//            mapUserPreferences()
//        }


    suspend fun incrementCounter() {
        this.dataStore.edit { settings ->
            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }
}
