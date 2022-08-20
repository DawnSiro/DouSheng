package com.qxy.dousheng.model.video

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayItem(
    @ColumnInfo(name = "video_url")
    val video_url: String?,
) {
    // 无需传入
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // 不设置为 0 会报异常
}