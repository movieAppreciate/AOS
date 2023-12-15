package com.proj.movieappreciate.util

import com.proj.movieappreciate.config.App
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService

class RetrofitUtil {
    companion object {
        val authService : AuthService = App.retrofit.create(AuthService::class.java)
    }
}