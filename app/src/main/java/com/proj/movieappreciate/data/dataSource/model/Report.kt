package com.proj.movieappreciate.data.dataSource.model

import java.sql.Timestamp

data class Report(
    val reportId : String?,
    var title : String,
    var content : String,
    val userId : String,
    val movieId : String,
    var tagString : String,
    val complainCount : Long,
    val createdDate : Timestamp?,
    val lastmodifiedDate : Timestamp?,
    ) {
    constructor(userId: String, title: String, content: String, movieId: String, tagString: String) : this(null, title, content, userId, movieId, tagString,0, null, null)
    constructor(reportId: String, userId: String, title: String, content: String, movieId: String, tagString: String, createdDate: Timestamp, lastmodifiedDate: Timestamp) : this(reportId, title, content, userId, movieId, tagString,0, createdDate, lastmodifiedDate)
}
