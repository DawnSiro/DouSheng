package com.qxy.dousheng.model.info

import androidx.room.Entity

/**
 * 获取用户公开信息 JSON 对象
 */
@Entity
data class InfoJson(
    var data: InfoItem = InfoItem(),
    var extra: InfoExtra = InfoExtra()
)


data class InfoExtra(
    var logid: String = "",
    var now: Long = 0
)