package com.qxy.dousheng.model.rank

/**
 * 获取抖音电影榜、抖音电视剧榜、抖音综艺榜 JSON 对象
 */
data class RankJson(
    var data: RankData,
    var extra: RankExtra
)

data class RankData(
    var active_time: String = "",
    var description: String = "",
    var error_code: Int = 0,
    var list: Array<RankItemList> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RankData

        if (active_time != other.active_time) return false
        if (description != other.description) return false
        if (error_code != other.error_code) return false
        if (!list.contentEquals(other.list)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = active_time.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + error_code
        result = 31 * result + list.contentHashCode()
        return result
    }
}

data class RankItemList(
    var actors: Array<String> = arrayOf(""),
    val areas: Array<String> = arrayOf(""),
    var directors: Array<String> = arrayOf(""),
    var discussion_hot: Long = 0,
    var hot: Long = 0,
    var id: String = "",
    var influence_hot: Long = 0,
    var maoyan_id: String = "",
    var name: String = "",
    var name_en: String = "",
    var poster: String = "",
    var release_date: String = "",
    var search_hot: Long = 0,
    var topic_hot: Long = 0,
    var tags: Array<String> = arrayOf(""),
    var type: Int = 1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RankItemList

        if (!actors.contentEquals(other.actors)) return false

        return true
    }

    override fun hashCode(): Int {
        return actors.contentHashCode()
    }
}

data class RankExtra(
    var description: String = "",
    var error_code: Int = 0,
    var logid: String = "",
    var now: Long = 0,
    val sub_description: String = "",
    val sub_error_code: String = ""
)