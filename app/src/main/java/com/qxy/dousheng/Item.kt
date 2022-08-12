package com.qxy.dousheng

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val name: String,
    val poster: String,
    val time: String,
    val hot: String
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
}
 */