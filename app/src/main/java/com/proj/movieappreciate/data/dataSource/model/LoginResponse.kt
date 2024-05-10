package com.proj.movieappreciate.data.dataSource.model

data class LoginResponse(
    val uid: String,
    val type: String,
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

