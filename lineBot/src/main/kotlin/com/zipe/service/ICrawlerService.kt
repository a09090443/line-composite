package com.zipe.service

interface ICrawlerService {

    /**
     * 搜尋 Ptt 表特版圖片
     */
    fun crawlerPttBeautyBoard(board: String, keyWords: List<String>, deepLevel: Int)

    fun saveImageUrlFromPtt(name: String, images: List<String>)
}
