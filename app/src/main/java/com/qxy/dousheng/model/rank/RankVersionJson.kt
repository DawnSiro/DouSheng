package com.qxy.dousheng.model.rank

data class RankVersionJson(
    var data: RankVersionData = RankVersionData(),
    var extra: RankVersionExtra = RankVersionExtra()
)

data class RankVersionData(
    var cursor: Int = 0,
    var description: String = "",
    var error_code: Int = 0,
    var has_more: Boolean = false,
    var list: List<RankVersionList> = listOf()
)

data class RankVersionList(
    var active_time: String = "",
    var end_time: String = "",
    var start_time: String = "",
    var type: Int = 1,
    var version: Int = 0
)

data class RankVersionExtra(
    var logid: String = "",
    var now: Long = 0,
    var description: String = "",
    var error_code: Int = 0,
    var sub_description: String = "",
    var sub_error_code: Int = 0
)