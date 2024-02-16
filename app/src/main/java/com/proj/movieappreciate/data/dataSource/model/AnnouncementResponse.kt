package com.proj.movieappreciate.data.dataSource.model

data class AnnouncementResponse(
    val notificationId : Int,
    val title : String,
    val content : String,
    val type : String,
    val createDate : String,
    val lastModifiedDate : String
)
