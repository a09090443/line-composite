package com.zipe.jdbc.criteria

enum class SQL(private val operator: String) {
    OR("OR"),
    IN("IN"),
    GT(">"),
    LT("<"),
    AND("AND"),
    EQUAL("="),
    GTEQUAL(">="),
    LTEQUAL("<="),
    ASC("ASC"),
    DESC("DESC"),
    LIKE("LIKE"),
    UNEQUAL("<>"),
    ISNULL("IS NULL"),
    NOTNULL("IS NOT NULL"),
    NOTEXISTS("NOT EXISTS");

    fun operator(): String {
        return operator
    }

}
