package com.zipe.service.impl

import com.zipe.entity.MessageDetail
import com.zipe.entity.MessageMapping
import com.zipe.entity.MessageSetting
import com.zipe.entity.asObject
import com.zipe.repository.IMessageDetailRepository
import com.zipe.repository.IMessageMappingRepository
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.ICrawlerService
import com.zipe.util.common.IMAGE
import com.zipe.util.common.IMAGE_JPG
import com.zipe.util.common.YES
import com.zipe.util.log.logger
import org.apache.commons.lang.StringUtils
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_18_ACCESS_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_BOARD_URL = "$PTT_DOMAIN/bbs/%s/index.html"
const val PTT_18_COOKIE_NAME = "over18"
const val USER_AGENT = "Mozilla"
const val IMAGE_JSON = """{
  "type": "image",
  "originalContentUrl": "%s",
  "previewImageUrl" : "%s"
}"""

//@Transactional
@Service
class CrawlerServiceImpl : ICrawlerService {

    @Autowired
    lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    lateinit var messageDetailRepository: IMessageDetailRepository

    @Autowired
    lateinit var messageMappingRepository: IMessageMappingRepository

    override fun crawlerPttBeautyBoard(board: String, keyWords: List<String>, deepLevel: Int) {

        var currentPage = getDoc(String.format(PTT_BOARD_URL, board))

        var previous: String

        // 根據條件往前搜尋設定的頁數
        val pages = mutableListOf<String>().apply {
            for (i in 0..deepLevel) {
                previous = currentPage.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
                currentPage = getDoc("$PTT_DOMAIN$previous")
                this.add("$PTT_DOMAIN$previous")
            }
        }

        // 根據關鍵字和讚數取得符合的網頁連結
        val links = pages.run {
            this.map { page ->
                getDoc(page)
                    .select("div[class=r-ent]")
                    .map { element -> getSubjectPages("[正妹]", 30, element) }
            }.flatten().toList()
        }

        val images = links.asSequence().filter { it.isNotBlank() }
            .map { link ->
                getDoc("$PTT_DOMAIN$link").select("a[rel]").map { it.attr("href") }
                    .filter { it.endsWith(IMAGE_JPG) }
            }.flatten().map { image -> String.format(IMAGE_JSON, image, image) }.toList()

        saveImageUrlFromPtt("抽", images)
    }

    @Throws(Exception::class)
    @Transactional(rollbackFor = [Exception::class])
    override fun saveImageUrlFromPtt(name: String, images: List<String>) {
        val messageSetting = messageSettingRepository.findAllByName(name) ?: messageSettingRepository.save(
            MessageSetting(
                name = name,
                comment = StringUtils.EMPTY
            )
        )
//        messageSettingRepository.save(MessageSetting().asObject(key, StringUtils.EMPTY))
//        messageMappingRepository.deleteByMessageId(messageSetting.id)

//        messageMappingRepository.findDetailIdsByMessageId(messageSetting.id)?.map { messageDetailRepository.deleteById(it) }

//        var messageDetail: MessageDetail
        images.forEach {
//            val messageDetail = MessageDetail().asObject(it, IMAGE, StringUtils.EMPTY)
            val messageDetail =
                messageDetailRepository.save(MessageDetail(value = it, type = IMAGE, channelId = StringUtils.EMPTY))
            try {
                messageMappingRepository.save(
                    MessageMapping(
                        messageId = messageSetting.id,
                        detailId = messageDetail.id
                    )
                )
            } catch (e: Exception) {
                logger().error("Save message error.")
                throw e
            }
        }
    }

    /**
     * 登入表特版需從 cookie 取得滿 18 歲同意資訊，並取得該頁所有 html 元素
     */
    private fun getDoc(url: String) =
        Jsoup.connect(url).userAgent(USER_AGENT).cookie(PTT_18_COOKIE_NAME, getPttAdultCookie()).get()

    private fun getPttAdultCookie(): String {
        val response =
            Jsoup.connect(PTT_18_ACCESS_URL).data(YES.toLowerCase(), YES.toLowerCase()).method(Connection.Method.POST)
                .execute()
        return response.cookie(PTT_18_COOKIE_NAME)
    }

    private fun getSubjectPages(keyWord: String, minimalStars: Int, element: Element): String {
        val goodStars = element.select("div[class=nrec]").text()
        val title: String

        if (goodStars.isNotBlank() and !goodStars.startsWith("X")) {
            if (goodStars == "爆" || goodStars.toInt() > minimalStars) {
                title = element.select("div[class=title] > a[href]").text()
                if (title.contains(keyWord)) {
                    return element.select("div[class=title] > a[href]").attr("href")
                }
            }
        }
        return StringUtils.EMPTY
    }
}

