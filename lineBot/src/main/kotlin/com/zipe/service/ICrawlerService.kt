package com.zipe.service

interface ICrawlerService {

    fun saveImageUrlFromPtt(channelId: String, key:String, images: List<String>)
}
