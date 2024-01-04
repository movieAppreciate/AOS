package com.proj.movieappreciate.util

import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import com.proj.movieappreciate.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(remoteDataSource: RemoteDataSource): AuthRepository {
        return AuthRepository(remoteDataSource)
    }
}