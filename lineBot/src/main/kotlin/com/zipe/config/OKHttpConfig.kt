package com.zipe.config

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


@Configuration
class OKHttpConfig {
    @Bean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    @Bean
    fun pool(): ConnectionPool {
        return ConnectionPool(50, 5, TimeUnit.MINUTES)
    }

    @Bean
    fun x509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(x509Certificates: Array<X509Certificate?>?, s: String?) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(x509Certificates: Array<X509Certificate?>?, s: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    }

    @Bean
    fun sslSocketFactory(): SSLSocketFactory {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            val xTrustArray = arrayOf(x509TrustManager())
            sslContext.init(
                    null,
                    xTrustArray, SecureRandom()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sslContext!!.socketFactory
    }
}
