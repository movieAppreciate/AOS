package com.proj.movieappreciate.data.repository

import com.proj.movieappreciate.data.dataSource.model.Report
import com.proj.movieappreciate.data.dataSource.model.SignUpResponse
import com.proj.movieappreciate.data.dataSource.remote.ReportRemoteDataSource
import javax.inject.Inject

class ReportRepository @Inject constructor(private val remoteDataSource: ReportRemoteDataSource) {

    suspend fun getAllReport(): Result<List<Report>> {
        return try {
            val response = remoteDataSource.getAllReport()
            Result.success(response.body()!!)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReportWithId(reportId : String): Result<Report> {
        return try {
            val response = remoteDataSource.getReportWithId(reportId)
            Result.success(response.body()!!)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchReportWithKeyWord(keyword : String): Result<List<Report>> {
        return try {
            val response = remoteDataSource.searchReportWithKeyWord(keyword)
            Result.success(response.body()!!)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registReprot(report : Report) : Result<Unit>{
        return try {
            val response = remoteDataSource.registReprot(report)
            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun remoteReprot(reportId : String) : Result<Unit>{
        return try {
            val response = remoteDataSource.remoteReport(reportId)
            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}