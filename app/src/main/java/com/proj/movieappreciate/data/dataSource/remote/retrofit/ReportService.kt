package com.proj.movieappreciate.data.dataSource.remote.retrofit

import com.proj.movieappreciate.data.dataSource.model.Report
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {



    @GET("report/report/getAllReports")
    suspend fun getAllReports() : Response<List<Report>>

    @GET("report/report/search/{keyword}")
    suspend fun searchReportWithKeyWord(@Path("keyword") keyword : String) : Response<List<Report>>

    @GET("report/getReport//{reportId}")
    suspend fun getReportWithId(@Path("reportId") reportId : String) : Response<Report>

    @GET("/report/deleteReport/{reportId}")
    suspend fun remoteReport(@Path("reportId") reportId : String)

    @POST("/report/registReport")
    suspend fun registReprot(@Body report : Report)

}