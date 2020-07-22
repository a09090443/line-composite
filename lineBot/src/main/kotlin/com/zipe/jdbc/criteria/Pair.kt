package com.zipe.jdbc.criteria

/**
 * 客製化Condition類別的參數組成資料類別
 *
 * @author adam.yeh
 * @create date: NOV 19, 2017
 */
class Pair {
    var column: String
    var value: String? = null
    var matchType: SQL
    var values: List<String>? = null

    constructor(column: String, matchType: SQL) {
        this.column = column
        this.matchType = matchType
    }

    constructor(column: String, value: String?, matchType: SQL) {
        this.column = column
        this.value = value
        this.matchType = matchType
    }

    constructor(column: String, values: List<String>?, matchType: SQL) {
        this.column = column
        this.values = values
        this.matchType = matchType
    }

}
