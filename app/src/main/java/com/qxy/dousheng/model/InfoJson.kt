package com.qxy.dousheng.model

import androidx.room.Entity

@Entity
data class InfoJson(
    var data: InfoItem = InfoItem(),
    var extra: InfoExtra = InfoExtra()
)


data class InfoExtra(
    var logid: String = "",
    var now: Long = 0
)