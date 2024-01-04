package com.proj.movieappreciate.data.dataSource.remote.retrofit

import com.proj.movieappreciate.data.dataSource.model.UserDTO
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun signUp(uid : String, type : String, profileURL : String): Response<SignUpResponse>
    suspend fun login(uid : String, type : String): Response<LoginResponse>
}