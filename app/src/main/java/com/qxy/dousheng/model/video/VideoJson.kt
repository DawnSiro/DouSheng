package com.qxy.dousheng.model.video

/**
 * 查询授权账号视频列表 JSON 对象
 */
data class VideoJson(
    var data: VideoData = VideoData(),
    var extra: VideoExtra = VideoExtra()
)

data class VideoExtra(
    var logid: String = "",
    var now: Long = 0,
    var error_code: Int = 0,
    var description: String = "",
    var sub_error_code: Int = 0,
    var sub_description: String = ""
)

data class VideoData(
    var error_code: Int = 0,
    var description: String = "",
    var has_more: Boolean = false,
    var cursor: Long = 0,
    var list: Array<VideoList> = arrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoData

        if (error_code != other.error_code) return false
        if (description != other.description) return false
        if (has_more != other.has_more) return false
        if (cursor != other.cursor) return false
        if (!list.contentEquals(other.list)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error_code
        result = 31 * result + description.hashCode()
        result = 31 * result + has_more.hashCode()
        result = (31 * result + cursor).toInt()
        result = 31 * result + list.contentHashCode()
        return result
    }
}

data class VideoList(
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
    var statistics: VideoStatistics = VideoStatistics()
)

data class VideoStatistics(
    var forward_count: Int = 0,
    var digg_count: Int = 0,
    var download_count: Int = 0,
    var play_count: Int = 0,
    var share_count: Int = 0,
    var comment_count: Int = 0
)