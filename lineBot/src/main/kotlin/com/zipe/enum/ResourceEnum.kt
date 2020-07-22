package com.zipe.enum

/**
 * 記錄Class path下特定目錄的資源檔位址
 *
 * @author adam.yeh
 * @create date: NOV 16, 2017
 */
enum class ResourceEnum(private val dir: String, private val extension: String) {
    /**
     * @param dir       資源擋路徑
     * @param extension 檔案類型
     */
    SQL("/sql", ".sql"),

    /**
     * Line相關資料
     */
    SQL_LINE("/sql/line", ".sql");

    private var file: String? = null
    fun getResource(name: String): ResourceEnum {
        file = "/$name"
        return this
    }

    fun file(): String? {
        return file
    }

    fun dir(): String {
        return dir
    }

    fun extension(): String {
        return this.extension
    }

}
