package com.qxy.dousheng.model

/**
 * 获取 access_token JSON 对象
 */
data class AccessTokenJson(
    var data: AccessTokenData = AccessTokenData(),
    var message: String = "",
    var extra: AccessTokenExtra = AccessTokenExtra()
)

data class AccessTokenData(
    var access_token: String = "",
    var description: String = "",
    var error_code: Int = -1,
    var expires_in: Int = 0,
    var open_id: String = "",
    var refresh_expires_in: Int = 0,
    var refresh_token: String = "",
    var scope: String = "",
    var captcha: String = "",
    var desc_url: String = "",
    var log_id: String = ""
)

data class AccessTokenExtra(
    val logid: String = "",
    val now: String = ""
)