package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.data.dataSource.model.Report
import retrofit2.Response

interface ReportRemoteDataSource {

    suspend fun getAllReport() : Response<List<Report>>

    suspend fun searchReportWithKeyWord(keyword : String) : Response<List<Report>>


    suspend fun getReportWithId(reportId : String) : Response<Report>


    suspend fun remoteReport( reportId : String)


    suspend fun registReprot(report : Report)
}