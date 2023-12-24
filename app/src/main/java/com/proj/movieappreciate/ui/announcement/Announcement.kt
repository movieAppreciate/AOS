package com.proj.movieappreciate.ui.announcement

data class Announcement(
    val date:String,
    val type:String,
    val title:String,
    val description:String,
    var expand:Boolean = false
)
