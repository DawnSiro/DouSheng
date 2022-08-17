package com.qxy.dousheng.model

data class InfoJson(
    var data: InfoData = InfoData(),
    var extra: InfoExtra = InfoExtra()
)

data class InfoData(
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
)

data class InfoExtra(
    var logid: String = "",
    var now: Int = 0
)