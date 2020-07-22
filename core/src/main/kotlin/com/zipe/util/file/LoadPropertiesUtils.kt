package com.zipe.util.file

import java.util.*

class LoadPropertiesUtils(fileName: String): Properties() {

    private val content = javaClass.classLoader.getResource(fileName).openStream()

    init {
        this.load(content)
    }

}
