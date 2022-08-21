package com.qxy.dousheng.model

data class FansCheckJson(
    var data: DataDTO = DataDTO(),
    var extra: ExtraDTO? = null
)

data class DataDTO(
    var follow_time: Int? = null,
    var is_follower: Boolean = false,
    var error_code: Int? = null,
    var description: String? = null
)

data class ExtraDTO(
    var error_code: Int? = null,
    var description: String? = null,
    var sub_error_code: Int? = null,
    var sub_description: String? = null,
    var now: Long? = null,
    var logid: String? = null
)
