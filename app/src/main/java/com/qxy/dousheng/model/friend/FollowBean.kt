package com.qxy.dousheng.model.friend

class FollowBean {
    var data: DataDTO = DataDTO()
    var extra: ExtraDTO = ExtraDTO()

    class DataDTO {
        var cursor: Int? = null
        var has_more: Boolean? = null
        var list: List<ListDTO> = ArrayList<ListDTO>()
        var total: Int? = null
        var error_code: Int? = null
        var description: String? = null

        class ListDTO {
            var avatar: String = ""
            var city: String = ""
            var country: String = ""
            var gender: Int = 0
            var nickname: String = ""
            var open_id: String = ""
            var province: String = ""
            var union_id: String = ""
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