package com.proj.movieappreciate.util

import android.content.Context
import com.proj.movieappreciate.data.dataSource.remote.AnnouncementDataSource
import com.proj.movieappreciate.data.dataSource.remote.AuthRemoteDataSource
import com.proj.movieappreciate.data.dataSource.remote.ReportRemoteDataSource
import com.proj.movieappreciate.data.repository.AnnounceRepository
import com.proj.movieappreciate.data.repository.AuthRepository
import com.proj.movieappreciate.data.repository.ReportRepository
import com.proj.movieappreciate.data.token.JwtTokenManager
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
    fun provideAuthRepository(remoteDataSource: AuthRemoteDataSource,tokenManager: JwtTokenManager): AuthRepository {
        return AuthRepository(remoteDataSource,tokenManager)
    }

    @Provides
    @Singleton
    fun provideReportRepository(remoteDataSource: ReportRemoteDataSource): ReportRepository {
        return ReportRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAnnounceRepository(remoteDataSource : AnnouncementDataSource) :AnnounceRepository {
        return AnnounceRepository(remoteDataSource)
    }

}