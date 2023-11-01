package com.mozhimen.componentk.pagingk.bases

/**
 * @ClassName BasePaingKRep
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:02
 * @Version 1.0
 */
class BasePagingKRep<RES> {
    constructor()

    constructor(code: Int, msg: String?) {
        this.code = code
        this.msg = msg
    }

    constructor(code: Int, msg: String?, data: PagingKData<RES>) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    //////////////////////////////////////////////////////

    /**
     * code : 1
     * msg : 查询成功
     * data : [{"id":"3","name":"我的","sortNum":2,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:36:02"},{"id":"2","name":"排行榜","sortNum":1,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:35:47"},{"id":"1","name":"发现","sortNum":0,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:35:26"}]
     */
    var code = 0
    var msg: String? = null
    var data: PagingKData<RES>? = null

    //////////////////////////////////////////////////////

    fun isSuccessful(): Boolean {
        return code == 1
    }

    override fun toString(): String {
        return "BasePagingKRep(code=$code, msg=$msg, data=$data)"
    }

    //////////////////////////////////////////////////////

    class PagingKData<RES> {
        /**
         * current : 0
         * pages : 0
         * records : [{"context":"string","id":"string","isDel":0,"lastUpdateTime":"2020-10-29T07:59:25.286Z","likeCount":0,"lvl":0,"releaseTime":"2020-10-29T07:59:25.286Z","replyCommentId":"string","replyCount":0,"replyUserId":0,"score":0,"status":0,"subjectId":"string","subjectType":0,"userId":0}]
         * searchCount : true
         * size : 0
         * total : 0
         */
        var current = 0//当前页码
        var pages = 0//总页数
        var size = 0//一页条数
        var total = 0//总条数
        var records: List<RES>? = null

        override fun toString(): String {
            return "PagingKData(current=$current, pages=$pages, size=$size, total=$total, records=$records)"
        }
    }
}