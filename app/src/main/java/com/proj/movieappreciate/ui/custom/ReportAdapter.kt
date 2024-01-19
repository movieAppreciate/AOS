package com.proj.movieappreciate.ui.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proj.movieappreciate.data.dataSource.model.Report
import com.proj.movieappreciate.databinding.MovieCommentaryItemBinding
import com.proj.movieappreciate.util.AdapterUtil

class ReportAdapter(var context : Context) : ListAdapter<Report, ReportAdapter.ItemViewHolder>(AdapterUtil.diffUtilReport) {

    inner class ItemViewHolder(var binding: MovieCommentaryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Report) = with(binding) {
            binding.commentaryTitleView.text = data.title
            binding.commentaryBodyView.text = data.content
            binding.nextCommentaryPageButton.setOnClickListener {
                itemClickListener.onClick(binding, layoutPosition, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            MovieCommentaryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: MovieCommentaryItemBinding, position: Int, data: Report)
    }

}