package com.zipe.enum

enum class SheduleJobStatusEmun(var status: Int, var desc: String) {
    DELETE(0, "DELETE"),
    START(1, "START"),
    SUSPEND(2, "SUSPEND"),
    RESUME(3, "RESUME"),
    CREATE(4, "CREATE");

}