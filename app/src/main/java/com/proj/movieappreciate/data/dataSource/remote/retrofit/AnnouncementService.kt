package com.proj.movieappreciate.data.dataSource.remote.retrofit

import com.proj.movieappreciate.data.dataSource.model.AnnouncementResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AnnouncementService {
    @POST("notification/regist")
    suspend fun registAnnouncement(
        @Query("title") title : String,
        @Query("content") content : String,
        @Query("type") type : String
    ) : Response<AnnouncementResponse>
}