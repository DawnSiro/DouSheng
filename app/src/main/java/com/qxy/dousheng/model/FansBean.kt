package com.qxy.dousheng.model


class FansBean {
    var data: DataDTO? = null
    var extra: ExtraDTO? = null

    class DataDTO {
        var cursor: Int? = null
        var has_more: Boolean? = null
        var list: List<ListDTO>? = null
        var total: Int? = null
        var error_code: Int? = null
        var description: String? = null

        class ListDTO {
            var avatar: String? = null
            var city: String? = null
            var country: String? = null
            var gender: Int? = null
            var nickname: String? = null
            var open_id: String? = null
            var province: String? = null
            var union_id: String? = null
        }
    }

    class ExtraDTO {
        var logid: String? = null
        var error_code: Int? = null
        var description: String? = null
        var sub_error_code: Int? = null
        var sub_description: String? = null
        var now: Int? = null
    }
}
