package com.zipe.util.file

import java.io.*

object FileUtil {

    /**
     * 创建文件
     * @param filePath 文件路径(不要以/结尾)
     * @param fileName 文件名称（包含后缀,如：ReadMe.txt）
     * @throws IOException
     */
    @Throws(IOException::class)
    fun createTxtFile(filePath: String, fileName: String): Boolean {
        var flag = false
        val filename = File("$filePath/$fileName")
        if (!filename.exists()) {
            filename.createNewFile()
            flag = true
        }
        return flag
    }

    /**
     * 写文件
     *
     * @param content 文件内容
     * @param filePath 文件路径(不要以/结尾)
     * @param fileName 文件名称（包含后缀,如：ReadMe.txt）
     * 新内容
     * @throws IOException
     */
    fun writeTxtFile(content: String, filePath: String, fileName: String, append: Boolean): Boolean {
        var flag: Boolean = true
        val thisFile = File("$filePath/$fileName")
        try {
            if (!thisFile.parentFile.exists()) {
                thisFile.parentFile.mkdirs()
            }
            val fw = FileWriter("$filePath/$fileName", append)
            fw.write(content)
            fw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return flag
    }

    /**
     * 读TXT文件内容
     * @param filePath 文件路径(不要以 / 结尾)
     * @param fileName 文件名称（包含后缀,如：ReadMe.txt）
     * @return
     */
    @Throws(Exception::class)
    fun readTxtFile(filePath: String, fileName: String): String? {
        val fileName = File("$filePath/$fileName")
        return readTxtFile(fileName)
    }

    @Throws(Exception::class)
    fun readTxtFile(file: File): String {
        var result = ""

        var fileReader: FileReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            fileReader = FileReader(file)
            bufferedReader = BufferedReader(fileReader)
            try {
                var read: String? = null
                while ({ read = bufferedReader.readLine();read }() != null) {
                    result = result + read + "\r\n"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bufferedReader?.close()
            fileReader?.close()
        }
        println("读取出来的文件内容是：\r\n$result")
        return result
    }
}

//fun main(args: Array<String>) {
//    val service = FileUtil
//    val pathName = "/sql/message/"
//    val fileName = "FIND_LINE_CHANNEL.sql"
//    val content = "我现在在上班" +
//            "比较忙的时候别来打扰我"
////    service.createTxtFile(pathName, fileName)
////    service.writeTxtFile(content, pathName, fileName, false)
//    val str = service.readTxtFile(pathName, fileName)
//    println(str)
//}
