package com.qxy.dousheng

data class Item(
    var data: Data,
    var extra: Extra
)

data class Data(
    var active_time: String = " ",
    var description: String = " ",
    var error_code: Int = 0,
    var list: Array<ItemList> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

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

data class ItemList(
    var actors: Array<String> = arrayOf(" "),
    val areas: Array<String> = arrayOf(" "),
    var directors: Array<String> = arrayOf(" "),
    var discussion_hot: Int = 0,
    var hot: Int = 0,
    var id: String = " ",
    var influence_hot: Int = 0,
    var maoyan_id: String = " ",
    var name: String = " ",
    var name_en: String = " ",
    var poster: String = " ",
    var release_date: String = " ",
    var search_hot: Int = 0,
    var topic_hot: Int = 0,
    var type: Int = 1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemList

        if (!actors.contentEquals(other.actors)) return false

        return true
    }

    override fun hashCode(): Int {
        return actors.contentHashCode()
    }
}

data class Extra(
    var description: String = " ",
    var error_code: Int = 0,
    var logid: String = " ",
    var now: Long = 0,
    val sub_description: String = " ",
    val sub_error_code: String = " "
)

/*
{
    "data": {
    "active_time": "2020-03-31 12:00:00",
    "description": "",
    "error_code": "0",
    "list": [
    {
        "actors": [
        "[徐峥 袁泉 沈腾 吴云芳 陈奇 黄梅莹 欧丽娅 贾冰 郭京飞]"
        ],
        "areas": [
        "[中国]"
        ],
        "directors": [
        "[徐峥]"
        ],
        "discussion_hot": "789200",
        "hot": "1.361e+06",
        "id": "6399487713065566465",
        "influence_hot": "789200",
        "maoyan_id": "1250696",
        "name": "囧妈",
        "name_en": "Lost in Russia",
        "poster": "https://p3-dy.bytecdn.cn/obj/compass/9ac412ae054b57f69c0967a8eb5e25c9",
        "release_date": "2020-01-25",
        "search_hot": "684900",
        "tags": [
        "[喜剧]"
        ],
        "topic_hot": "684900",
        "type": "1"
    }
    ]
    "extra": {
        "description": "",
        "error_code": "0",
        "logid": "202008121419360101980821035705926A",
        "now": "1597213176393",
        "sub_description": "",
        "sub_error_code": "0"
  }
}
 */