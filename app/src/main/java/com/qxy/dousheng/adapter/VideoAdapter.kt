package com.qxy.dousheng.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.util.Log
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

        // TODO("BUG:相对于当前时间的 时间戳")
        holder.time.text =
            "创建时间：" + SimpleDateFormat("YYYY-MM-DD").format(videoItem.create_time * 1000)
        Log.d("okHttp", (videoItem.create_time / 1000 / 60 / 60 / 24).toString())

        holder.count.text =
            "播放：${videoItem.play_count} 点赞：${videoItem.digg_count} 评论：${videoItem.comment_count}"
        if (!videoItem.is_top) {
            holder.top.visibility = View.INVISIBLE
        }

        // 设置点击事件
        holder.itemView.setOnClickListener {
            val uri = Uri.parse(videoItem.share_url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = videoList.size
}