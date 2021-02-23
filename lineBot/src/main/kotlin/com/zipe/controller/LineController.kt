package com.zipe.controller

import com.google.common.io.ByteStreams
import com.linecorp.bot.model.event.CallbackRequest
import com.zipe.base.controller.BaseController
import com.zipe.service.line.ILineActionService
import com.zipe.service.IMessageSettingService
import com.zipe.util.JsonUtil
import com.zipe.util.SINGNATURE
import com.zipe.util.log.logger
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@Api(tags=["LineController"], description = "Line bot api")
@RestController
@RequestMapping(value = ["/line"])
class LineController(
    private val lineActionServiceImpl: ILineActionService,
    private val messageSettingServiceImpl: IMessageSettingService
) : BaseController() {

    /**
     * Line 頻道設定 Webhook URL verify 網址
     * 需在 Line Message API 的 Webhook URL 設定 : https://domain/line/channel-secret/callback
     * Line 會依照訊息類別回傳至該 URI，需處理不同之 Events
     */
    @ApiOperation(value="callback", notes="Line 接收訊息 Api")
    @RequestMapping(value = ["/{channelSecret}/callback"], method = [RequestMethod.GET, RequestMethod.POST])
    fun handleCallback(@PathVariable channelSecret: String): ResponseEntity<String> {
        val signature = request.getHeader(SINGNATURE)
        val body = ByteStreams.toByteArray(request.inputStream)
        val callbackRequest = JsonUtil.lineJsonMapper().readValue(body, CallbackRequest::class.java)
        lineActionServiceImpl.isSignatureValid(channelSecret, signature, body).takeIf { !it }?.let {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        callbackRequest.events.map { lineActionServiceImpl.eventHandler(it, channelSecret) }

        return ResponseEntity(HttpStatus.OK)
    }

    /**
     * 使用頻道設定的 message 寄送給指定使用者
     */
    @ApiOperation(value="send", notes="寄送給指定使用者")
    @PostMapping(value = ["/send"])
    fun send(
        @RequestParam(value = "key") key: String,
        @RequestParam(value = "toUsers") toUsers: List<String>,
        @RequestParam(value = "channelId") channelId: String,
        @RequestParam(value = "type") type: String
    ): String {

        messageSettingServiceImpl.findMessages(key, channelId).let { messageData ->
            if (messageData.isNullOrEmpty()) {
                return "Can not found message."
            }

            val users = lineActionServiceImpl.getUserId(channelId, toUsers, listOf(type))
            users.isNullOrEmpty().takeIf { it }?.let { return "Can not find user" }
            toUsers.filterNot { user -> users.map { it.name }.toList().contains(user) }.let {
                it.forEach { username -> logger().warn("Can not find username : $username in database") }
            }
            val ids = users.map { user -> user.lineId }.toList()
            lineActionServiceImpl.pushFromJson(ids, messageData.random().value, channelId)
        }
        return "send message success"
    }

    @PostMapping(value = ["/sendPayment"])
    fun sendPayment() {

//        val json = """{"amount":100,"currency":"TWD","orderId":"53b55346-bf6b-4a9a-855a-cd937464525c","packages":[{"id":"44433234dsa","name":"shop_name","amount":100,"products":[{"id":"product_id","name":"product_name","quantity":10,"price":10}]}],"redirectUrls":{"confirmUrl":"http://localhost/confirm","cancelUrl":"http://localhost/cencel"}}"""
//        val tttt = lineActionService.paymentProcess(json, LineChannel())
//        val messageSetting = messageSettingService.findBykey("捐錢")
////        lineActionService.pushFromJson("C67b997253bf2da1487a3752c658a2b61",
////                String.format(messageSetting.messageDetails.first().value, tttt), "健人工程師",
////                false)
    }

    /**
     * Line pay 付費後從 Line 官網回傳的確認網址
     */
    @ApiOperation(value="confirm", notes="Line pay 付費後從 Line 官網回傳的確認網址")
    @GetMapping(value = ["/payment/confirm"])
    fun confirm(
        @RequestParam("orderId") orderId: String,
        @RequestParam("transactionId") transactionId: String
    ): ModelAndView {

        val botId = lineActionServiceImpl.paymentConfirm(transactionId.toLong())

        return ModelAndView("redirect:line://ti/p/$botId")
    }

    /**
     * 取消 Line pay
     */
    @ApiOperation(value="cancel", notes="取消 Line pay")
    @GetMapping(value = ["/payment/cancel"])
    fun cancel(@RequestParam("transactionId") transactionId: String): ModelAndView {
        return ModelAndView("redirect:line://ti/p/@020lrinf")
    }
}
