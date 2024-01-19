package com.proj.movieappreciate.util

import androidx.recyclerview.widget.DiffUtil
import com.proj.movieappreciate.data.dataSource.model.Report

class AdapterUtil {
    companion object
    {
        val diffUtilReport = object : DiffUtil.ItemCallback<Report>() {
            override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem.reportId == newItem.reportId
            }

            override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
                return oldItem == newItem
            }

        }

    }
}