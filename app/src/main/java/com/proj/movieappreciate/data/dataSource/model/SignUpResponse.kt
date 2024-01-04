package com.proj.movieappreciate.data.dataSource.model

import java.sql.Time

data class SignUpResponse(
    val uid : String,
    val userId : String,
    val type : String,
    val nickname : String,
    val profileURL : String,
    val lastLoginDate : Time,
    val createDate : Time,
    val lastModifiedDate : Time,

)
