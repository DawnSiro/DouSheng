package com.qxy.dousheng.model.info

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据实体类，对应数据库中的表
 */
@Entity
data class InfoItem(
    @ColumnInfo
    var avatar: String = "",
    var avatar_larger: String = "",
    var captcha: String = "",
    var city: String = "",
    var client_key: String = "",
    var country: String = "",
    var desc_url: String = "",
    var description: String = "",
    var district: String = "",
    var e_account_role: String = "",
    var error_code: Int = 0,
    var gender: Int = 0,
    var log_id: String = "",
    var nickname: String = "",
    var open_id: String = "",
    var province: String = "",
    var union_id: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}