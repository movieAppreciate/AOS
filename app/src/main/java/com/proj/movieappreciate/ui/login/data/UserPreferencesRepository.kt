package com.proj.movieappreciate.ui.login.data

import android.content.Context
import android.os.Build.VERSION_CODES.P
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.viewModelFactory
import com.proj.movieappreciate.config.App.Companion.ACCESS_TOKEN_KEY
import com.proj.movieappreciate.config.App.Companion.datastore
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class UserPreferencesRepository @Inject constructor(
    @ApplicationContext context: Context) {

    private val datastore = context.datastore

    val userAccessTokenFlow: Flow<String> =datastore.data
        .catch {
            exception -> if(exception is IOException) {
                emit(emptyPreferences())
        }
            else{
                throw exception
        }
        }
        .map {
                preferences -> preferences[ACCESS_TOKEN_KEY] ?: ""
        }


    fun getAccessToken():Flow<String?> {
        return datastore.data.map {
            preferences ->preferences[ACCESS_TOKEN_KEY]
        }

    }
    suspend fun saveUserAccessToken(userAccessToken : String) {
        datastore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = userAccessToken
        }

    }

}


