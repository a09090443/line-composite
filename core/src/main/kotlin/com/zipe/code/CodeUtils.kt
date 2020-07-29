package com.zipe.code

import java.util.*
import kotlin.experimental.and

/**
 * 编码工具
 */
object CodeUtils {

    val charString = charArrayOf('a', 'b', 'c', 'd', 'e', 'f')

    fun toUnicode(str: String): String {
        val arChar = str.toCharArray()
        var iValue = 0
        var uStr = ""
        for (i in arChar.indices) {
            iValue = str[i].toInt()
            if (iValue <= 256) {
                // uStr+="& "+Integer.toHexString(iValue)+";";
                uStr += "\\" + Integer.toHexString(iValue)
            } else {
                // uStr+="&#x"+Integer.toHexString(iValue)+";";
                uStr += "\\u" + Integer.toHexString(iValue)
            }
        }
        return uStr
    }

    fun unicodeToGB(s: String): String {
        val sb = StringBuffer()
        val st = StringTokenizer(s, "\\u")
        while (st.hasMoreTokens()) {
            sb.append(Integer.parseInt(st.nextToken(), 16).toChar())
        }
        return sb.toString()
    }

    fun toUnicodeString(s: String): String {
        val sb = StringBuffer()
        for (i in 0 until s.length) {
            val c = s[i]
            if (c.toInt() >= 0 && c.toInt() <= 255) {
                sb.append(c)
            } else {
                sb.append("\\u" + Integer.toHexString(c.toInt()))
            }
        }
        return sb.toString()
    }

    /**
     * 将字节转换为十六进制的字符串（）
     *
     * @param bytesCommand
     * @return
     * @from 忘了
     */
    fun bytesToHexString(bytesCommand: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (bytesCommand == null || bytesCommand.isEmpty()) {
            return null
        }
        for (i in bytesCommand.indices) {
            val v = bytesCommand[i] and 0xFF.toByte()
            val hv = Integer.toHexString(v.toInt())
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }

    /**
     * 将十六进制的字符串转换成字节
     *
     * @param commandStr 7E 18 00 07 00 04 01 02 03 04 00 05 00 1A 7E
     * @return
     * @throws NumberFormatException
     */
    @Throws(NumberFormatException::class)
    fun parseCommand(commandStr: String): ByteArray {
        val tempStr = commandStr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val commands = ByteArray(tempStr.size)
        for (i in tempStr.indices) {
            try {
                commands[i] = Integer.parseInt(tempStr[i], 16).toByte()
            } catch (o_o: Exception) {
                commands[i] = 0
//                Log.e("命令转换出错", tempStr[i])
            }

        }
        return commands
    }
}
