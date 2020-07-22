package com.zipe.util

import org.jsoup.Connection
import org.jsoup.Jsoup

//fun main() {
//    val response = Jsoup.connect("https://www.ptt.cc/ask/over18").data("yes", "yes").method(Connection.Method.POST).execute()
//    val cookie: String = response.cookie("over18")
//
//    var doc = Jsoup.connect("https://www.ptt.cc/bbs/Beauty/index.html")
//            .userAgent("Mozilla").cookie("over18", cookie).get()
//
//    var previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
//    for (i in 0 until 5) {
//        doc = Jsoup.connect("https://www.ptt.cc$previous").userAgent("Mozilla").cookie("over18", cookie).get()
//        previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
//        println(previous)
//
//        val list = doc.select("div[class=r-ent]").forEach {
//            val links = it.select("div[class=title]>a[href]")
//            for (link in links) {
//                doc = Jsoup.connect("https://www.ptt.cc${link.attr("href")}").userAgent("Mozilla").cookie("over18", cookie).get()
//                println("link : ${link.attr("href")}")
//                val images = doc.select("a[rel]").map { it.attr("href") }.toList()
//                println(images)
////                images.forEach {
////                    val href = it.attr("href")
////                    val text = it.text()
////                    println("$text - $href")
////                }
//
//
////                println("image : " + doc.select("img[src$=.jpg]"))
//            }
//        }
//    }
//    println(previous)
////    val list = doc.select("div[class=r-ent]").forEach {
////        val links = it.select("div[class=title]>a[href]")
////        for (link in links) {
////            println("link : ${link.attr("href")}")
////            println("text : " + link.text())
////        }
////
////    }
//
////    println(list.size)
////    val first: String = doc.select("div[class=r-ent]>div[class=title]>a")[0].text()
////    println(first)
////    val doc = Jsoup.connect("https://www.ptt.cc/bbs/Beauty/index.html").get()
////    val test = doc.select("img[src$=.jpg]")
////    println(test)
//}
