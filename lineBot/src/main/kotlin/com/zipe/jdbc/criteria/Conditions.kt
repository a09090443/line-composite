package com.zipe.jdbc.criteria

import org.apache.commons.lang3.StringUtils

/**
 * JDBC DAO的查詢條件物件化類別
 *
 * @author adam.yeh
 * @create date: NOV 19, 2017
 */
class Conditions {
    private var condition = StringBuilder()
    fun equal(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.EQUAL))
        return this
    }

    fun like(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.LIKE))
        return this
    }

    fun unEqual(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.UNEQUAL))
        return this
    }

    fun `in`(column: String, values: List<String>): Conditions {
        appendPairTypes(Pair(column, values, SQL.IN))
        return this
    }

    fun gt(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.GT))
        return this
    }

    fun lt(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.LT))
        return this
    }

    fun gtEqual(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.GTEQUAL))
        return this
    }

    fun ltEqual(column: String, value: String): Conditions {
        appendPairTypes(Pair(column, value, SQL.LTEQUAL))
        return this
    }

    fun notNull(column: String): Conditions {
        appendPairTypes(Pair(column, SQL.NOTNULL))
        return this
    }

    fun isNull(column: String): Conditions {
        appendPairTypes(Pair(column, SQL.ISNULL))
        return this
    }

    fun notExists(value: String): Conditions {
        appendPairTypes(Pair("", value, SQL.NOTEXISTS))
        return this
    }

    /**
     * 左括弧
     *
     * @return
     */
    fun leftPT(): Conditions {
        condition!!.append(" (")
        return this
    }

    /**
     * 左括弧
     *
     * @param e 連結符號 ( AND、OR... )
     * @return
     */
    fun leftPT(e: SQL): Conditions {
        condition.append(" " + e.operator() + " (")
        return this
    }

    /**
     * 右括弧
     *
     * @return
     */
    fun rightPT(): Conditions {
        condition.append(") ")
        return this
    }

    /**
     * 右括弧
     *
     * @param e 連結符號 ( AND、OR... )
     * @return
     */
    fun rightPT(e: SQL): Conditions {
        condition.append(") " + e.operator() + " ")
        return this
    }

    /**
     * 將組裝好的條件句參數AND起來
     *
     * @return
     */
    fun and(): Conditions {
        condition.append(" " + SQL.AND.operator() + " ")
        return this
    }

    /**
     * 將組裝好的條件句參數OR起來
     *
     * @return
     */
    fun or(): Conditions {
        condition.append(" " + SQL.OR.operator() + " ")
        return this
    }
    /**
     * 排序
     *
     * @param order 升/降 冪
     * @return
     */
    /**
     * 排序
     *
     * @return
     */
    @JvmOverloads
    fun orderBy(column: String, order: SQL = SQL.ASC): Conditions {
        condition.append(" ORDER BY $column $order.operator() ")
        return this
    }

    /**
     * 宣告條件句結束
     *
     * @return
     */
    fun done(sqlText: String): String {
        val done: String = StringUtils.replace(sqlText, "\${CONDITIONS}", condition.toString())
        condition.clear()
        return done
    }

    private fun appendPairTypes(pair: Pair) {
        var pair: Pair? = pair
        val column: String? = pair?.column
        val value: String? = pair?.value
        val type: SQL? = pair?.matchType
        val operator: String? = type?.operator()
        val values: List<String>? = pair?.values
        when (type) {
            SQL.IN -> {
                val sqlText: StringBuilder = StringBuilder()
                sqlText.append("( '")
                sqlText.append(StringUtils.join(values, "', '"))
                sqlText.append("' )")
                condition.append("$column $operator $sqlText")
                sqlText.clear()
            }
            SQL.LIKE -> condition.append("$column $operator '%$value%'")
            SQL.NOTEXISTS -> condition.append(" $operator ( $value ) ")
            SQL.ISNULL, SQL.NOTNULL -> condition.append("$column $operator")
            else -> condition.append("$column $operator '$value'")
        }
        pair = null
    }

}
