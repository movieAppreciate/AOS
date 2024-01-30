package com.proj.movieappreciate.data.dataSource.remote

import com.proj.movieappreciate.data.dataSource.model.Report
import com.proj.movieappreciate.data.dataSource.remote.retrofit.ReportService
import retrofit2.Response
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(private val reportService: ReportService): ReportRemoteDataSource{
    override suspend fun getAllReport(): Response<List<Report>> {
        return reportService.getAllReports()
    }

    override suspend fun searchReportWithKeyWord(keyword: String): Response<List<Report>> {
        return reportService.searchReportWithKeyWord(keyword)
    }

    override suspend fun getReportWithId(reportId: String): Response<Report> {
        return reportService.getReportWithId(reportId)
    }

    override suspend fun remoteReport(reportId: String) {
        return remoteReport(reportId)
    }

    override suspend fun registReprot(report: Report) {
        return registReprot(report)
    }
}