package com.zipe.test.repository

import com.zipe.entity.LineChannel
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.LineChannelJDBC
import com.zipe.repository.ILineChannelRepository
import com.zipe.test.base.TestBase
import org.junit.Ignore
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate

class LineChannelRepositoryTest : TestBase() {

    @Autowired
    lateinit var lineChannelRepository: ILineChannelRepository

    @Autowired
    lateinit var lineChannelJDBC: LineChannelJDBC

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Ignore
    @Test
    fun `test to get channel by channel secret`() {
//        val channel = lineChannelRepository.findLineInfoInChannelByLineId("703c0f44af4957ab84b0e59b344dc26e", "U21d2289790fe0d2e5d01e20a314a8caa")
        val test = lineChannelRepository.findByChannelSecret("703c0f44af4957ab84b0e59b344dc26e")

        println(test)
    }

    @Test
    fun `test find channel by Jdbc`() {

        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_LINE_CHANNEL")
        val sql = """select lc.* from line_channel lc where lc.channel_secret = '703c0f44af4957ab84b0e59b344dc26e'"""
        val channel = jdbcTemplate.query(sql, BeanPropertyRowMapper(LineChannel::class.java))
//        val test = lineChannelJDBC.queryForBean(resource, LineChannel::class.java)
        println(channel)
    }

    @Ignore
    @Test
    fun `test find by line id`(){
        val test = lineChannelRepository.findById(4)
        println(test)
    }

    @Ignore
    @Test
    fun `test insert channel`() {

//        val lineInfo = LineInfo(infoId = 1, lineId = "U21d2289790fe0d2e5d01e20a314a8caa", name = "天賜良機", statusMessage = "人生低潮中...", type = "user")
//        val lineChannel = LineChannel(
//                channelSecret = "703c0f44af4957ab84b0e59b344dc26e"
//        )
//        lineChannel.lineInfos = listOf(lineInfo)
//        lineChannelRepository.save(lineChannel)
    }

}
