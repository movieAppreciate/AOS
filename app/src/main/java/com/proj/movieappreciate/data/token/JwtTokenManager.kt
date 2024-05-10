package com.proj.movieappreciate.data.token

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.proj.movieappreciate.config.App.Companion.ACCESS_TOKEN_KEY
import com.proj.movieappreciate.config.App.Companion.REFRESH_TOKEN_KEY
import com.proj.movieappreciate.config.App.Companion.datastore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class JwtTokenManager @Inject constructor(
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

    suspend fun saveRefreshToken(refreshToken:String) {
        datastore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun deleteAccessToken() {
        datastore.edit { preferences -> preferences.remove(ACCESS_TOKEN_KEY) }
    }

}


