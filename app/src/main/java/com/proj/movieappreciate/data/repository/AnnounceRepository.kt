package com.proj.movieappreciate.data.repository

import android.util.Log
import com.proj.movieappreciate.data.dataSource.model.AnnouncementResponse
import com.proj.movieappreciate.data.dataSource.remote.AnnouncementDataSource
import com.proj.movieappreciate.data.dataSource.remote.retrofit.AnnouncementService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


class AnnounceRepository @Inject constructor(
    private val announcementDataSource: AnnouncementDataSource){
    suspend fun registAnnouncement(
        title : String,
        content : String,
        type : String) : Result<AnnouncementResponse> {
        return try {
            val response = announcementDataSource.registAnnouncement(title, content, type)

            Log.d("공지 등록", response.body().toString())
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            }
            else   {
                Result.failure(Exception("regist announcement failed"))
            }
        }
        catch (e:Exception) {
            Result.failure(e)
        }
    }
}