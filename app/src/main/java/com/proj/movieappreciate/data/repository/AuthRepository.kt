package com.proj.movieappreciate.data.repository

import android.util.Log
import com.proj.movieappreciate.data.dataSource.model.UserDTO
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.dataSource.remote.retrofit.RemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    class SignUpException : Exception("SignUp")
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
    suspend fun login(uid: String, type : String): Result<LoginResponse> {
        return try {
            val response = remoteDataSource.login(uid, type)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                if(response.code() == 401){
                    Log.d("로그인 ", "login: 401에러 발생")
                    throw SignUpException()
                }
                else{
                    Result.failure(Exception("Login failed"))
                }

            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}