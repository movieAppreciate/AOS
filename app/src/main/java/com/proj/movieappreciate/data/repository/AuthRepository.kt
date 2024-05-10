package com.proj.movieappreciate.data.repository

import android.util.Log
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.dataSource.remote.AuthRemoteDataSource
import com.proj.movieappreciate.data.token.JwtTokenManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    val tokenManager: JwtTokenManager
) {

    class SignUpException : Exception("SignUp")
    suspend fun signUp(uid : String, type : String, profileURL : String): Result<SignUpResponse> {
        return try {
            val response = remoteDataSource.signUp(uid ,type, profileURL )

            Log.d("로그인 signup body",response.toString())
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
                //todo : datastore에 access token 저장해주기
                Log.d("로그인 액세스 토큰", response.body()?.accessToken.toString())
                tokenManager.saveUserAccessToken(response.body()?.accessToken.toString())
                Log.d("로그인 데이터스토어", tokenManager.getAccessToken().first().toString())

                Result.success(response.body()!!)
            } else {
                if(response.code() == 401){
                    Log.d("로그인 ", "login: 401에러 발생")
                    throw SignUpException()
                }
                else{
                    Log.d("로그인 레포",response.errorBody().toString())
                    Result.failure(Exception("Login failed"))
                }

            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}