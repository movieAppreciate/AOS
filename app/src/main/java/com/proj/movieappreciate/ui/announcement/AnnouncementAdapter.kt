package com.proj.movieappreciate.ui.announcement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proj.movieappreciate.databinding.AnnouncementListItemBinding

class AnnouncementAdapter(val listAnnouncement: List<Announcement>):RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding:AnnouncementListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AnnouncementListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAnnouncement.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder) {
            with(listAnnouncement[position]) {
                binding.dateTv.text = this.date
                binding.typeTv.text = this.type
                binding.titleTv.text = this.title
                binding.descriptionTv.text = this.description
                binding.layoutExpand.visibility = if (this.expand) View.VISIBLE else View.GONE

                holder.binding.moviesItem.setOnClickListener {
                    this.expand = !this.expand
                    notifyItemChanged(position)
                }
            }
        }
    }

}