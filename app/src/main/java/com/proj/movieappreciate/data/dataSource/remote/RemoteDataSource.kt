package com.proj.movieappreciate.data.dataSource.remote.retrofit

import com.proj.movieappreciate.data.dataSource.model.LoginData
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpData
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Path

interface RemoteDataSource {
    suspend fun signUp(uid : String, type : String, profileURL : String): Response<SignUpResponse>
    suspend fun login(loginData: LoginData): Response<LoginResponse>
}