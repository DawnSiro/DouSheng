package com.qxy.dousheng.model.friend

/**
 * 粉丝判断接口的 JSON 对象
 */
data class FansCheckJson(
    var data: DataDTO = DataDTO(),
    var extra: ExtraDTO = ExtraDTO()
)

data class DataDTO(
    var follow_time: Int = 0,
    var is_follower: Boolean = false,
    var error_code: Int = 0,
    var description: String = ""
)

data class ExtraDTO(
    var error_code: Int = 0,
    var description: String = "",
    var sub_error_code: Int = 0,
    var sub_description: String = "",
    var now: Long = 0,
    var logid: String = ""
)
