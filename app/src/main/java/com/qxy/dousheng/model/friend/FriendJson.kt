package com.qxy.dousheng.model.friend

class FriendJson {
    var data: DataDTO = DataDTO()
    var extra: ExtraDTO = ExtraDTO()

    class DataDTO {
        var cursor: Int = 0
        var has_more: Boolean = false
        var list: Array<ListDTO> = arrayOf()
        var total: Int = 0
        var error_code: Int = 0
        var description: String = ""

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
        var logid: String = ""
        var error_code: Int = 0
        var description: String = ""
        var sub_error_code: Int = 0
        var sub_description: String = ""
        var now: Int = 0
    }
}
