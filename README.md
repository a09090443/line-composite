[![Line](https://aleen42.github.io/badges/src/line.svg)](https://developers.line.biz/zh-hant/)
[![official JetBrains project](https://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![JDK](http://img.shields.io/badge/JDK-v8.0-yellow.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![MariaDB](https://img.shields.io/badge/MariaDB-10.5.8-blue.svg?logo=mariadb)](https://mariadb.org/)
[![Spring Boot](https://img.shields.io/badge/springboot-v2.4.2-green.svg?logo=spring)](https://spring.io/projects/spring-framework)
[![Gradle](https://img.shields.io/badge/gradle-6.5.1-green.svg?logo=gradle)](https://spring.io/projects/spring-framework)

Line Bot Project
======================
## 事前準備
至 Line developer 註冊帳號並建立聊天機器人
* [Line documentation](https://developers.line.biz/zh-hant/docs/messaging-api/building-bot/ "Line 官方文件")

## 開發環境
* OpenJDK 1.8
* Kotlin 1.4
* Spring boot 2.4
* Redis latest
* Gradle latest
* Postman latest
* MariaDB 10.5.8

## 所依賴的 projects
* [Core](https://github.com/a09090443/core "核心專案")

## 專案目錄結構圖
<img src="./images/projects.png" style="zoom:70%" />

## 各專案說明
* core - 為核心專案，提供各種基本設定
* lineBot - 為 Line bot 後端程式
* management - 暫定為前端程式，尚未開發

## 啟動方式
1. `#cd lineBot`
2. `#gradle bootRun`

## Line 聊天室操作影片
1. line bot + line pay 自動購買流程</br>
   [![line bot + line pay 自動購買流程](https://res.cloudinary.com/marcomontalbano/image/upload/v1613891666/video_to_markdown/images/youtube--c6dJqFawIVs-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://www.youtube.com/watch?v=c6dJqFawIVs&ab_channel=%E8%94%A1%E4%BF%8A%E5%82%91 "line bot + line pay 自動購買流程")

2. line bot + line pay 自動購買流程</br>
   [![line bot 自動回應](https://res.cloudinary.com/marcomontalbano/image/upload/v1613891737/video_to_markdown/images/youtube--G0r3YF8Dp98-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://www.youtube.com/watch?v=G0r3YF8Dp98&ab_channel=%E8%94%A1%E4%BF%8A%E5%82%91 "line bot 自動回應")

## 備註
* [Line 官方文件](https://developers.line.biz/zh-hant/docs/messaging-api/)
