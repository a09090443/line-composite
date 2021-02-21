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
1. ####line bot + line pay 自動購買流程
[![line bot + line pay 自動購買流程](http://img.youtube.com/vi/c6dJqFawIVs/0.jpg)](http://www.youtube.com/watch?v=c6dJqFawIVs "line bot + line pay 自動購買流程")

2. ####line bot + line pay 自動購買流程
[![line bot 自動回應](http://img.youtube.com/vi/G0r3YF8Dp98/0.jpg)](http://www.youtube.com/watch?v=G0r3YF8Dp98 "line bot 自動回應")

## 備註
* [Line 官方文件](https://developers.line.biz/zh-hant/docs/messaging-api/)
