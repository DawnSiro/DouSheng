package com.qxy.dousheng.model

class FriendJson {
    var data: DataDTO = DataDTO()
    var extra: ExtraDTO = ExtraDTO()

    class DataDTO {
        var cursor: Int = 0
        var has_more: Boolean = false
        var list: Array<ListDTO> = arrayOf()
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