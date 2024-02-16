package com.proj.movieappreciate.util

import com.proj.movieappreciate.data.dataSource.remote.AnnouncementDataSource
import com.proj.movieappreciate.data.dataSource.remote.AnnouncementDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.AuthRemoteDataSource
import com.proj.movieappreciate.data.dataSource.remote.AuthRemoteDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.RemoteDataSourceImpl
import com.proj.movieappreciate.data.dataSource.remote.ReportRemoteDataSource
import com.proj.movieappreciate.data.dataSource.remote.ReportRemoteDataSourceImpl
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

//    @Provides
//    @Singleton
//    fun provideRemoteDataSource(): RemoteDataSource {
//        return RemoteDataSourceImpl(RetrofitUtil.authService, RetrofitUtil.reportService) // 여기에서 RemoteDataSource를 생성하여 반환하도록 작성해야 합니다.
//    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(RetrofitUtil.authService)
    }


    @Provides
    @Singleton
    fun provideReportRemoteDataSource(): ReportRemoteDataSource {
        return ReportRemoteDataSourceImpl(RetrofitUtil.reportService)
    }

    @Provides
    @Singleton
    fun provideAnnouncementDataSource() : AnnouncementDataSource {
        return AnnouncementDataSourceImpl(RetrofitUtil.announcementService)
    }
}