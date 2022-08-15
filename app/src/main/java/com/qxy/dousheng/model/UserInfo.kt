package com.qxy.dousheng.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "nation")
    val nation: String,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "profile")
    val profile: String)



/*


{
    "data": {
        "avatar": "https://p3.douyinpic.com/aweme/100x100/aweme-avatar/tos-cn-i-0813_c38017ebe4f14a9a9a81e98e6112bd2a.jpeg?from=4010531038",
        "avatar_larger": "https://p3.douyinpic.com/aweme/1080x1080/aweme-avatar/tos-cn-i-0813_c38017ebe4f14a9a9a81e98e6112bd2a.jpeg?from=4010531038",
        "captcha": "",
        "city": "",
        "client_key": "awr4g04kxg26jk2l",
        "country": "",
        "desc_url": "",
        "description": "",
        "district": "",
        "e_account_role": "",
        "error_code": 0,
        "gender": 0,
        "log_id": "202208151950450102080160860D0C48F7",
        "nickname": "DawnSiro",
        "open_id": "_000E77KMHNLpL-XFNPzk8DofgVpPAAk-e0Y",
        "province": "",
        "union_id": "1521d2d8-0acd-494f-9e5a-59194192b02c"
    },
    "message": "success"
}
 */