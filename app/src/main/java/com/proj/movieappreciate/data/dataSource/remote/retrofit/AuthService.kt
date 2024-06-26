package com.proj.movieappreciate.data.dataSource.remote.retrofit

import com.proj.movieappreciate.data.dataSource.model.UserDTO
import com.proj.movieappreciate.data.dataSource.model.LoginResponse
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

private const val TAG = "AuthService"
interface AuthService {
    @POST("signup")
    suspend fun signUp(@Query("uid") uid : String, @Query("type") type : String, @Query("profileURL") profileURL : String) : Response<SignUpResponse>
    @POST("login")
    suspend fun login(@Query("uid") uid: String, @Query("type") type: String) : Response<LoginResponse>
}