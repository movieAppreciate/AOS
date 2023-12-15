package com.proj.movieappreciate.data.repository

import com.proj.movieappreciate.data.dataSource.model.LoginData
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpData
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun signUp(uid : String, type : String, profileURL : String): Result<SignUpResponse> {
        return try {
            val response = remoteDataSource.signUp(uid ,type, profileURL )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun login(loginData: LoginData): Result<LoginResponse> {
        return try {
            val response = remoteDataSource.login(loginData)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}