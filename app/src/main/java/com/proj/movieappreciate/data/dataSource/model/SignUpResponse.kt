package com.proj.movieappreciate.data.dataSource.model

import retrofit2.http.Body
import java.sql.Time


data class SignUpResponse(
    val userinfo : SignUpBody
){
    data class SignUpBody(
        val uid: String,
        val userId: String,
        val type: String,
        val nickname: String,
        val profileURL: String,
        val lastLoginDate: String,
        val createDate: String,

        )

}




