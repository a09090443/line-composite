package com.zipe.test.repository

import com.zipe.entity.LineInfo
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.test.base.TestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class LineInfoRepositoryTest : TestBase() {

    @Autowired
    lateinit var lineInfoRepository: ILineInfoRepository

    @Test
    fun `test to get line id by name`() {
        val lineId = lineInfoRepository.findByLineIdAndType("U21d2289790fe0d2e5d01e20a314a8ca1", "user")
        println(lineId)
    }

    @Test
    fun `test save user`() {
        lineInfoRepository.save(LineInfo(lineId = "U28d248c07703d3ded2d91e9421416f99", name = "蔡俊傑",
                statusMessage = null, type = LineType.USER.name))
    }

}
