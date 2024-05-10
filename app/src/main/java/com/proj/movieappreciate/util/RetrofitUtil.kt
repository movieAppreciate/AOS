package com.proj.movieappreciate.util

import com.proj.movieappreciate.config.App
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AnnouncementService
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService
import com.proj.movieappreciate.data.dataSource.remote.retrofit.ReportService
import retrofit2.create

class RetrofitUtil {
    companion object {
        val authService : AuthService = App.retrofit.create(AuthService::class.java)

        val reportService : ReportService = App.retrofit.create(ReportService::class.java)

        val announcementService : AnnouncementService = App.retrofit.create(AnnouncementService::class.java)
    }
}