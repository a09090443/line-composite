package com.zipe.util.common

import com.zipe.model.UserInfo
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails

open class UserInfoUtil {

    companion object{
        private val authentication: Authentication = SecurityContextHolder.getContext().authentication

        fun getUserInfo(): UserInfo {
            return UserInfo().apply {
                this.name = authentication.name
                this.authorizations = authentication.authorities.map { it.authority }.toList()
                val detail = SecurityContextHolder.getContext().authentication.details
                if(detail is OAuth2AuthenticationDetails){
                    this.ip = detail.remoteAddress
                }
            }
        }
    }

}
