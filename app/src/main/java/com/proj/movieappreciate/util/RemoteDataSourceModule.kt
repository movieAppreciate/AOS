package com.proj.movieappreciate.util

import com.proj.movieappreciate.data.dataSource.remote.RemoteDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService
import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSourceImpl(RetrofitUtil.authService) // 여기에서 RemoteDataSource를 생성하여 반환하도록 작성해야 합니다.
    }
}