package com.zipe.test.service

import com.linecorp.bot.model.event.CallbackRequest
import com.linecorp.bot.model.objectmapper.ModelObjectMapper
import com.zipe.enum.EventType
import com.zipe.service.line.ILineActionService
import com.zipe.test.base.TestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class LineActionServiceTest : TestBase() {

    @Autowired
    lateinit var lineActionService: ILineActionService

    @Test
    fun `test validate signature`() {

//    val testService:ILineEventService = SpringUtil.getBean(LineUnfollowEventServiceImpl::class.java)
        val testService = EventType.getService(EventType.JOINEVENT.name)
//        println(testService.p)
        val signature = "zD+bSKoP5vBl1uxIq0sUG4DX+NeWrkiUYtJyyFpTxo4="
        val channelSecurity = "703c0f44af4957ab84b0e59b344dc26e"
        val body = """{"events":[{"type":"message","replyToken":"4d973966de794b3988c2c53ef606a0a7","source":{"userId":"U21d2289790fe0d2e5d01e20a314a8caa","type":"user"},"timestamp":1594005877819,"mode":"active","message":{"type":"text","id":"12268740706643","text":"衝"}}],"destination":"U387d03e1ef5698a654f72efa71294504"}"""
        val event = ModelObjectMapper.createNewObjectMapper().readValue(body.toByteArray(), CallbackRequest::class.java)
        println(event::class.java)
//        val test = Gson().fromJson(body, Event::class.java)
//        println(test)
        val validation = lineActionService.isSignatureValid(channelSecurity, signature, body.toByteArray()).takeIf { !it }?.let {
            println(it)
        }
    }

}
