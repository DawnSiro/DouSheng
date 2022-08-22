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
import com.qxy.dousheng.model.video.VideoItem

/**
 * Adapter 适配器层
 * 持有数据集合对象，将数据同步更新到视图上
 */
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

        // 对播放量、点赞数、评论数进行量级处理
        var playCount: String = if(videoItem.play_count > 10000){
            (videoItem.play_count / 10000).toString() + "万"
        }else{
            videoItem.play_count.toString()
        }

        var diggCount: String = if(videoItem.digg_count > 10000){
            (videoItem.digg_count / 10000).toString() + "万"
        }else{
            videoItem.digg_count.toString()
        }

        var commentCount: String = if(videoItem.comment_count > 10000){
            (videoItem.comment_count / 10000).toString() + "万"
        }else{
            videoItem.comment_count.toString()
        }


        holder.count.text =
            "播放：$playCount 点赞：$diggCount 评论：$commentCount"
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