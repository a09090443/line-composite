package com.zipe.jdbc.criteria

import com.zipe.enum.ResourceEnum
import com.zipe.util.FileUtil

/**
 * 實現伺服器端分頁的資料物件<br></br>
 * P.S. 如果需要使用伺服器端分頁的SQL需要排序, 直接使用Paging.orderBy來排序。
 * @author adam.yeh
 */
abstract class Paging {
    private val pageingTemplate: String? = null

    // 當前頁碼
    var page = 0

    // 每頁顯示筆數
    var pagesize = 0

    // 總資料筆數
    var recordsTotal = 0

    // 總資料筆數(顯示於頁角)
    var recordsFiltered = 0

    // 需要使用哪些欄位排序
    var orderBy: List<String>? = null

    companion object {
        var pagingSQL: String? = null

        init {
            val resource: ResourceEnum = ResourceEnum.SQL.getResource("PAGING")
            val path = StringBuilder()
            path.append(resource.dir())
            path.append(resource.file())
            path.append(resource.extension())
            pagingSQL = FileUtil.readTxtFile(path.toString(), "")
        }
    }

    open fun getPagingSQL(): String? {
        return pageingTemplate
    }
}
