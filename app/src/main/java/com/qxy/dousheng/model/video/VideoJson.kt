package com.qxy.dousheng.model.video

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

/*
{
  "extra": {
    "error_code": 0,
    "description": "",
    "sub_error_code": 0,
    "sub_description": "",
    "logid": "202008121419360101980821035705926A",
    "now": 1597213176393
  },
  "data": {
    "error_code": 0,
    "description": "",
    "has_more": false,
    "list": [
      {
        "title": "测试视频 #测试话题 @抖音小助手",
        "is_top": false,
        "create_time": 1571075129,
        "is_reviewed": true,
        "video_status": 5,
        "share_url": "https://www.iesdouyin.com/share/video/QDlWd0EzdWVMU2Q0aU5tKzVaOElvVU03ODBtRHFQUCtLUHBSMHFRT21MVkFYYi9UMDYwemRSbVlxaWczNTd6RUJRc3MrM2hvRGlqK2EwNnhBc1lGUkpRPT0=/?region=CN&mid=6753173704399670023&u_code=12h9je425&titleType=title",
        "item_id": "@8hxdhauTCMppanGnM4ltGM780mDqPP+KPpR0qQOmLVAXb/T060zdRmYqig357zEBq6CZRp4NVe6qLIJW/V/x1w==",
        "media_type": 2,
        "cover": "https://p3-dy.byteimg.com/img/tos-cn-p-0015/cfa0d6421bdc4580876cb16974539ff6~c5_300x400.jpeg",
        "statistics": {
          "forward_count": 10,
          "comment_count": 100,
          "digg_count": 200,
          "download_count": 10,
          "play_count": 300,
          "share_count": 10
        }
      }
    ],
    "cursor": 0
  }
}
 */