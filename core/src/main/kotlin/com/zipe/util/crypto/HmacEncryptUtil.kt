package com.zipe.util.crypto

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils


object HmacEncryptUtil {

    fun encrypt(keys: String, data: String): String {
        return toBase64String(HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, keys.toByteArray()).doFinal(data.toByteArray()))
    }

    private fun toBase64String(bytes: ByteArray): String {
        val byteArray = Base64.encodeBase64(bytes)
        return String(byteArray)
    }

}
