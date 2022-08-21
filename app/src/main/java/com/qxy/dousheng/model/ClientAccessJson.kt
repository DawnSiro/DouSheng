package com.qxy.dousheng.model

data class ClientAccessJson(
    var data: ClientData = ClientData(),
    var message: String = "",
    var extra: ClientExtra = ClientExtra()
)


data class ClientData(
    var access_token: String = "",
    var captcha: String = "",
    var desc_url: String = "",
    var description: String = "",
    var error_code: String = "",
    var expires_in: String = "",
    var log_id: String = ""
)

data class ClientExtra(
    var logid: String = "",
    var now: Long = 0
)