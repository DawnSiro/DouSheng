package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qxy.dousheng.R
import com.qxy.dousheng.model.VideoItem

class VideoAdapter(var videoList: List<VideoItem>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.findViewById(R.id.videoCoverImageView)
        val title: TextView = view.findViewById(R.id.videoTitleTextView)
        val time: TextView = view.findViewById(R.id.videoTimeTextView)
        val count: TextView = view.findViewById(R.id.videoCountTextView)
        val top: TextView = view.findViewById(R.id.videoTopTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat", "WeekBasedYear", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoItem = videoList[position]

        Glide.with(holder.itemView)
            .load(videoItem.cover)
            .into(holder.cover)

        holder.title.text = videoItem.title
        holder.time.text =
            "创建时间：" + SimpleDateFormat("YY-MM-DD-hh-mm-ss").format(videoItem.create_time)
        holder.count.text =
            "播放：${videoItem.play_count} 点赞：${videoItem.digg_count} 评论：${videoItem.comment_count}"
        if (!videoItem.is_top) {
            holder.top.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount() = videoList.size
}