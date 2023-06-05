package com.mozhimen.basick.cachek.datastore.helpers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.mozhimen.basick.cachek.commons.ICacheKProvider
import com.mozhimen.basick.utilk.content.UtilKApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class CacheKDSProvider(dsName: String) : ICacheKProvider {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = dsName)
    val dataStore: DataStore<Preferences> by lazy { UtilKApplication.instance.applicationContext._dataStore }

    /////////////////////////////////////////////////////////////////////

    fun <T> transform(key: String, value: T, mutablePreferences: MutablePreferences) {
        when (value) {
            is String -> mutablePreferences[stringPreferencesKey(key)] = value //putString(key, value)
            is Boolean -> mutablePreferences[booleanPreferencesKey(key)] = value
            is Int -> mutablePreferences[intPreferencesKey(key)] = value
            is Long -> mutablePreferences[longPreferencesKey(key)] = value
            is Float -> mutablePreferences[floatPreferencesKey(key)] = value
            is Double -> mutablePreferences[doublePreferencesKey(key)] = value
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        }
    }

    suspend fun <T> DataStore<Preferences>.editBy(key: String, value: T, transform: suspend (String, T, MutablePreferences) -> Unit): Preferences = this.updateData {
        it.toMutablePreferences().apply { transform(key, value, this) }
    }

    /////////////////////////////////////////////////////////////////////

    suspend fun <T> putObjAsync(key: String, value: T) {
        dataStore.editBy(key, value, ::transform)
    }

    suspend fun putStringAsync(key: String, value: String) {
        putObjAsync(key, value)
    }

    suspend fun putBooleanAsync(key: String, value: Boolean) {
        putObjAsync(key, value)
    }

    suspend fun putIntAsync(key: String, value: Int) {
        putObjAsync(key, value)
    }

    suspend fun putLongAsync(key: String, value: Long) {
        putObjAsync(key, value)
    }

    suspend fun putFloatAsync(key: String, value: Float) {
        putObjAsync(key, value)
    }

    suspend fun putDoubleAsync(key: String, value: Double) {
        putObjAsync(key, value)
    }

    suspend fun putStringSetAsync(key: String, value: Set<String>) {
        dataStore.edit { it[stringSetPreferencesKey(key)] = value }
    }

    /////////////////////////////////////////////////////////////////////

    override fun <T> putObj(key: String, obj: T) {
        runBlocking { putObjAsync(key, obj) }
    }

    override fun putString(key: String, value: String) {
        runBlocking { putStringAsync(key, value) }
    }

    override fun putBoolean(key: String, value: Boolean) {
        runBlocking { putBooleanAsync(key, value) }
    }

    override fun putInt(key: String, value: Int) {
        runBlocking { putIntAsync(key, value) }
    }

    override fun putLong(key: String, value: Long) {
        runBlocking { putLongAsync(key, value) }
    }

    override fun putFloat(key: String, value: Float) {
        runBlocking { putFloatAsync(key, value) }
    }

    override fun putDouble(key: String, value: Double) {
        runBlocking { putDoubleAsync(key, value) }
    }

    fun putStringSet(key: String, value: Set<String>) {
        runBlocking { putStringSetAsync(key, value) }
    }

    /////////////////////////////////////////////////////////////////////

    @Suppress("UNCHECKED_CAST")
    override fun <T> getObj(key: String, default: T): T =
        when (default) {
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            is Double -> getDouble(key, default)
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        } as T

    /////////////////////////////////////////////////////////////////////

    override fun getInt(key: String): Int =
        getInt(key, 0)

    override fun getInt(key: String, defaultValue: Int): Int =
        runBlocking { return@runBlocking dataStore.data.map { it[intPreferencesKey(key)] ?: defaultValue }.first() }

    override fun getLong(key: String): Long =
        getLong(key, 0L)

    override fun getLong(key: String, defaultValue: Long): Long =
        runBlocking { return@runBlocking dataStore.data.map { it[longPreferencesKey(key)] ?: defaultValue }.first() }

    override fun getString(key: String): String =
        getString(key, "")

    override fun getString(key: String, defaultValue: String): String =
        runBlocking { return@runBlocking dataStore.data.map { it[stringPreferencesKey(key)] ?: defaultValue }.first() }

    override fun getBoolean(key: String): Boolean =
        getBoolean(key, false)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        runBlocking { return@runBlocking dataStore.data.map { it[booleanPreferencesKey(key)] ?: defaultValue }.first() }

    override fun getFloat(key: String): Float =
        getFloat(key, 0f)

    override fun getFloat(key: String, defaultValue: Float): Float =
        runBlocking { return@runBlocking dataStore.data.map { it[floatPreferencesKey(key)] ?: defaultValue }.first() }

    override fun getDouble(key: String): Double =
        getDouble(key, 0.0)

    override fun getDouble(key: String, defaultValue: Double): Double =
        runBlocking { return@runBlocking dataStore.data.map { it[doublePreferencesKey(key)] ?: defaultValue }.first() }


    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> =
        runBlocking { return@runBlocking dataStore.data.map { it[stringSetPreferencesKey(key)] ?: defaultValue }.first() }

    /////////////////////////////////////////////////////////////////////

    inline fun <reified T> getPreferencesKey(key: String): Preferences.Key<out Any> =
        when (T::class) {
            String::class -> stringPreferencesKey(key)//putString(key, value)
            Boolean::class -> booleanPreferencesKey(key)
            Int::class -> intPreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            Double::class -> doublePreferencesKey(key)
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        }

    inline fun <reified T> remove(key: String) {
        runBlocking {
            val preferencesKey = getPreferencesKey<T>(key)
            dataStore.edit { if (it.contains(preferencesKey)) it.remove(preferencesKey) }
        }
    }

    inline fun <reified T> contains(key: String): Boolean =
        runBlocking { return@runBlocking dataStore.data.map { it.contains(getPreferencesKey<T>(key)) }.first() }

    override fun clear() {
        runBlocking { dataStore.edit { it.clear() } }
    }

//    // At the top level of your kotlin file:
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//    private val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
//
//    val exampleCounterFlow: Flow<Int> = this.dataStore.data
//        .map { preferences ->
//            // No type safety.
//            preferences[EXAMPLE_COUNTER] ?: 0
//        }
//
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
//
//
//    suspend fun incrementCounter() {
//        this.dataStore.edit { settings ->
//            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
//            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
//        }
//    }
}