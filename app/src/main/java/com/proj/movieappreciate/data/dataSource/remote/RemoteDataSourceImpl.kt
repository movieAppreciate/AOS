package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.config.App
import com.proj.movieappreciate.data.dataSource.model.LoginData
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpData
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AuthService

import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val authService : AuthService)  : RemoteDataSource{
    override suspend fun signUp(uid : String, type : String, profileURL : String): Response<SignUpResponse> {
        return authService.signUp(uid, type, profileURL)
    }

    override suspend fun login(loginData: LoginData): Response<LoginResponse> {
        return authService.login(loginData)
    }


}