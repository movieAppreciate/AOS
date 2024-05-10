package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.data.dataSource.model.AnnouncementResponse
import retrofit2.Response

interface AnnouncementDataSource {
    suspend fun registAnnouncement(title:String,content:String,type:String) : Response<AnnouncementResponse>
}