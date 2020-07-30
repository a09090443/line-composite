package com.zipe.controller

import com.google.common.io.ByteStreams
import com.linecorp.bot.model.event.CallbackRequest
import com.zipe.base.controller.BaseController
import com.zipe.entity.LineChannel
import com.zipe.service.ILineActionService
import com.zipe.service.IMessageSettingService
import com.zipe.util.JsonUtil
import com.zipe.util.SINGNATURE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping(value = ["/line"])
class LineController : BaseController() {

    @Autowired
    lateinit var lineActionService: ILineActionService

    @Autowired
    lateinit var messageSettingService: IMessageSettingService

    @PostMapping(value = ["/{channelSecret}/callback"])
    fun handleCallback(@PathVariable channelSecret: String): ResponseEntity<String> {
        val signature = request.getHeader(SINGNATURE)
        val body = ByteStreams.toByteArray(request.inputStream)
        val callbackRequest = JsonUtil.lineJsonMapper().readValue(body, CallbackRequest::class.java)
        lineActionService.isSignatureValid(channelSecret, signature, body).takeIf { !it }?.let {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        callbackRequest.events.map { lineActionService.eventHandler(it, channelSecret) }

        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping(value = ["/send"])
    fun send(@RequestParam(value = "key") key: String, @RequestParam(value = "to") to: String,
             @RequestParam(value = "channel") channel: String) {

        val messageSetting = messageSettingService.findBykey(key)

        val lineId = lineActionService.getLineIdByNameExcludeChannelType(to)
        val randomMessage = messageSetting.messageDetails.first()
        lineActionService.pushFromJson(lineId, randomMessage.value, channel, false)
    }

    @PostMapping(value = ["/sendPayment"])
    fun sendPayment() {

        val json = """{"amount":100,"currency":"TWD","orderId":"53b55346-bf6b-4a9a-855a-cd937464525c","packages":[{"id":"44433234dsa","name":"shop_name","amount":100,"products":[{"id":"product_id","name":"product_name","quantity":10,"price":10}]}],"redirectUrls":{"confirmUrl":"http://localhost/confirm","cancelUrl":"http://localhost/cencel"}}"""
        val tttt = lineActionService.paymentProcess(json, LineChannel())
        val messageSetting = messageSettingService.findBykey("捐錢")
//        lineActionService.pushFromJson("C67b997253bf2da1487a3752c658a2b61",
//                String.format(messageSetting.messageDetails.first().value, tttt), "健人工程師",
//                false)
    }

    @GetMapping(value = ["/payment/confirm"])
    fun confirm(@RequestParam("orderId") orderId: String,
                @RequestParam("transactionId") transactionId: String): ModelAndView {

        val botId = lineActionService.paymentConfirm(transactionId.toLong())

        return ModelAndView("redirect:line://ti/p/$botId")
    }

    @GetMapping(value = ["/payment/cancel"])
    fun cancel(@RequestParam("transactionId") transactionId: String) :ModelAndView{
        return ModelAndView("redirect:line://ti/p/@020lrinf")
    }
}
