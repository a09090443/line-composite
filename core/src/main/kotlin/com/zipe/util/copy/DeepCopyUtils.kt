package com.zipe.util.copy

import java.io.*

/**
 * @Description: 通过序列化/反序列化方式完成深复制
 * @Author wangjianzhou@qding.me
 * @Date 2019-08-10 09:55
 * @Version
 */
object DeepCopyUtils {
    /**
     * 要求data对象及其引用对象都实现了Serializable接口才可以用
     * 针对Object、数组、Map、List类型
     *
     * @param source
     * @param <T>
     * @return
     * @throws Exception
    </T> */
    fun <T> deepCopyObject(source: T?): T? {
        var dest: T? = null
        if (source == null) {
            return dest
        }
        try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(source)
            //从流里读出来
            val bais = ByteArrayInputStream(baos.toByteArray())
            val oi = ObjectInputStream(bais)
            dest = oi.readObject() as T
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return dest
    }

    /**
     * 要求data对象及其引用对象都实现了Serializable接口才可以用
     * 针对List类型
     *
     * @param src
     * @param <T>
     * @return
    </T> */
    fun <T> deepCopyList(src: List<T>?): List<T>? {
        var dest: List<T>? = null
        if (src == null) {
            return dest
        }
        try {
            val byteOut = ByteArrayOutputStream()
            val out = ObjectOutputStream(byteOut)
            out.writeObject(src)
            val byteIn = ByteArrayInputStream(byteOut.toByteArray())
            var `in`: ObjectInputStream? = null
            `in` = ObjectInputStream(byteIn)
            dest = `in`.readObject() as List<T>
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return dest
    }

    /**
     * 要求data对象及其引用对象都实现了Serializable接口才可以用
     * 针对数组类型
     *
     * @param source
     * @param <T>
     * @return
    </T> */
    fun <T> deepCopyArray(source: Array<T>?): Array<T>? {
        var dest: Array<T>? = null
        try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(source)
            //从流里读出来
            val bais = ByteArrayInputStream(baos.toByteArray())
            val oi = ObjectInputStream(bais)
            dest = oi.readObject() as Array<T>
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return dest
    }
}
