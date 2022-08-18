package com.qxy.dousheng.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoItem(
    var title: String = "",
    var cover: String = "",
    var create_time: Long = 0,
    var is_top: Boolean = false,
    var is_reviewed: Boolean = true,
    var video_status: Int = 0,
    var video_id: String = "",
    var share_url: String = "",
    var item_id: String = "",
    var media_type: Int = 0,
    var forward_count: Int = 0,
    var digg_count: Int = 0,
    var download_count: Int = 0,
    var play_count: Int = 0,
    var share_count: Int = 0,
    var comment_count: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}