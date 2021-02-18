package com.zipe.service

interface ICrawlerService {

    /**
     * 依照條件查詢 Ptt 資料
     */
    fun crawlerPttBoard(board: String, keyWords: List<String>, deepLevel: Int)

    /**
     * 從 Ptt 抓取到的圖片網址，儲存至 MessageDetail table 中
     */
    fun saveImageUrlFromPtt(name: String, images: List<String>)
}
