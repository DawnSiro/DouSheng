package com.qxy.dousheng.model.friend

class FansCheckBean {
    var data: DataDTO = DataDTO()
    var extra: ExtraDTO = ExtraDTO()

    class DataDTO {
        var follow_time: Int? = null
        var is_follower: Boolean? = null
        var error_code: Int? = null
        var description: String? = null
    }

    class ExtraDTO {
        var error_code: Int? = null
        var description: String? = null
        var sub_error_code: Int? = null
        var sub_description: String? = null
        var now: Int? = null
        var logid: String? = null
    }
}
