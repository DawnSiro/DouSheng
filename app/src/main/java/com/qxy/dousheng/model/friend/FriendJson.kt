package com.qxy.dousheng.model.friend

/**
 * 获取关注列表和粉丝列表的 JSON 对象
 */
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
        var now: Long = 0 // 这里用 Int 可能会超出范围。Long 最大值为 9 开头的 19 位数
    }
}
