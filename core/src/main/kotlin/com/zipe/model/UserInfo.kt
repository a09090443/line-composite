package com.zipe.model

data class UserInfo(

    var name: String = "",
    var authorizations: List<String> = listOf(),
    var ip: String = ""
)
