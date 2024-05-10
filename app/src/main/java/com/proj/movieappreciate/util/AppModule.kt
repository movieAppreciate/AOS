package com.proj.movieappreciate.util

import android.content.Context
import com.proj.movieappreciate.data.token.JwtTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Singleton
    @Provides
    fun providesUserPreferencesRepository(@ApplicationContext context : Context) : JwtTokenManager {
        return JwtTokenManager(context)
    }


}