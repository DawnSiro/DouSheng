package com.qxy.dousheng.model.rank

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Rank 榜单类
 * 数据实体类，对应数据库中的表
 */
@Entity
data class RankItem(
    @ColumnInfo(name = "directors")
    val directors: String,
    @ColumnInfo(name = "areas")
    val areas: String, // 上映区域
    @ColumnInfo(name = "actors")
    val actors: String,
    @ColumnInfo(name = "hot")
    val hot: Long,
    @ColumnInfo(name = "discussion_hot")
    val discussionHot: Long, // 讨论热度
    @ColumnInfo(name = "topic_hot")
    val topicHot: Long, // 主题热度
    @ColumnInfo(name = "search_hot")
    val searchHot: Long, // 搜索热度
    @ColumnInfo(name = "influence_hot")
    val influenceHot: Long, // 影响力热度
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "english_name")
    val englishName: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String, // 上映时间
    @ColumnInfo(name = "tags")
    val tags: String,
    @ColumnInfo(name = "type")
    val type: Int // 类型：1=电影 2=电视剧 3=综艺
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}