package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.data.dataSource.model.AnnouncementResponse
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AnnouncementService
import retrofit2.Response
import javax.inject.Inject

class AnnouncementDataSourceImpl @Inject constructor(
    private val announcementSerivce : AnnouncementService) : AnnouncementDataSource{

    override suspend fun registAnnouncement(
        title: String,
        content: String,
        type: String
    ): Response<AnnouncementResponse> {
        return announcementSerivce.registAnnouncement(title,content,type)
    }


}