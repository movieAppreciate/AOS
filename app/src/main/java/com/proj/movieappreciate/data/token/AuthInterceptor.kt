package com.proj.movieappreciate.data.token

import android.util.Log
import com.kakao.sdk.auth.TokenManager
import com.proj.movieappreciate.config.App.Companion.HEADER_AUTHORIZATION
import com.proj.movieappreciate.config.App.Companion.TYPE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject


// todo : request header에 access token 추가
class AuthInterceptor @Inject constructor(
    private val tokenManager: JwtTokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = runBlocking {
            tokenManager.getAccessToken().first()

        } ?: return errorResponse(chain.request())

        val request = chain.request()
            .newBuilder()
            .header(HEADER_AUTHORIZATION,"$TYPE $token")
            .build()

        return chain.proceed(request)
    }

    private fun errorResponse(request: Request) : Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .message("")
        .body(ResponseBody.create(null,""))
        .build()

}