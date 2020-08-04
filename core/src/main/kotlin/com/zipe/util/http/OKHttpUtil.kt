package com.zipe.util.http

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class OkHttpUtil {
    @Autowired
    lateinit var okHttpClient: OkHttpClient

    fun initPostRequest(url: String, body: RequestBody, headerMap: Map<String, String>): Request =
        with(Request.Builder()) {
            url(url)
            post(body)
            headerMap.forEach { (key, value) -> addHeader(key, value) }
            build()
        }

    fun initGetRequest(url: String): Request =
        with(Request.Builder()) {
            url(url)
            get()
            build()
        }

    interface IOkHttpCallback {
        fun onResponse(response: Response)

        fun onFailure(e: IOException)
    }

    //同步
    fun sync(call: Call): String =
        try {
            val response = call.execute()
            if (response.isSuccessful) response.body!!.string() else ""
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }

    //異步
    fun async(call: Call, callback: IOkHttpCallback) =
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
            }
        })

    //Get同步
    fun getSync(url: String): String? {
        var request = initGetRequest(url)
        return sync(okHttpClient.newCall(request))
    }

    //Get異步
    fun getAsync(url: String, callback: IOkHttpCallback) {
        val request = initGetRequest(url)
        async(okHttpClient.newCall(request), callback)
    }

    //Post同步:JSON - Not tested
    fun postSyncJSON(url: String, json: String, headerMap: Map<String, String>): String {
        val body = json.toRequestBody("application/json;charset=utf-8".toMediaType())
        val request = initPostRequest(url, body, headerMap)
        return sync(okHttpClient.newCall(request))
    }

    //Post同步:Map<String, String>
    fun postSyncForm(url: String, params: Map<String, String>?, headerMap: Map<String, String>): String? {
        val body = mapToBody(params)
        val request = initPostRequest(url, body, headerMap)
        return sync(okHttpClient.newCall(request))
    }

    //Post異步:JSON - Not tested
    fun postAsyncJSON(url: String, json: String, headerMap: Map<String, String>, callback: IOkHttpCallback) {
        val body = json.toRequestBody("application/json;charset=utf-8".toMediaType())
        val request = initPostRequest(url, body, headerMap)
        async(okHttpClient.newCall(request), callback)
    }

    //Post異步:Map<String, String>
    fun postAsyncForm(url: String, params: Map<String, String>?, headerMap: Map<String, String>, callback: IOkHttpCallback) {
        val body = mapToBody(params)
        val request = initPostRequest(url, body, headerMap)
        async(okHttpClient.newCall(request), callback)
    }

    fun cancel(call: Call) {
        for (dispatcherCal1 in okHttpClient.dispatcher.queuedCalls()) {
            if (call.request().tag()!! == call.request().tag()) call.cancel()
        }
        for (dispatcherCal1 in okHttpClient.dispatcher.runningCalls()) {
            if (call.request().tag()!! == call.request().tag()) call.cancel()
        }
    }

    fun cancelAll() {
        okHttpClient.dispatcher.cancelAll()
    }

    //轉換器 map to requestBody
    fun mapToBody(map: Map<String, String>?): RequestBody {
        val mainMap: Map<String, String> = map ?: HashMap()
        val builder = FormBody.Builder()
        for (entry in mainMap.entries) {
            builder.add(entry.key, entry.value)
        }
        return builder.build()
    }
}
