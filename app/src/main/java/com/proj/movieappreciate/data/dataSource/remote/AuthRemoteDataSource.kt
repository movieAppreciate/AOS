package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import retrofit2.Response

interface AuthRemoteDataSource {

    suspend fun signUp(uid : String, type : String, profileURL : String): Response<SignUpResponse>
    suspend fun login(uid : String, type : String): Response<LoginResponse>
}