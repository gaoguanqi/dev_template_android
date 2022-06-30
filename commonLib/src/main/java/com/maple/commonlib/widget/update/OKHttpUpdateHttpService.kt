package com.maple.commonlib.widget.update

import com.blankj.utilcode.util.GsonUtils
import com.xuexiang.xupdate.proxy.IUpdateHttpService
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception
import java.util.*
import com.zhy.http.okhttp.request.RequestCall
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import com.zhy.http.okhttp.callback.FileCallBack
import okhttp3.Request
import java.io.File


class OKHttpUpdateHttpService(private val isPostJson: Boolean = false) : IUpdateHttpService {

    override fun asyncGet(
        url: String,
        params: MutableMap<String, Any>,
        callBack: IUpdateHttpService.Callback
    ) {
        OkHttpUtils.get()
            .url(url)
            .params(transform(params))
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    callBack.onError(e)
                }

                override fun onResponse(response: String?, id: Int) {
                    callBack.onSuccess(response)
                }
            })
    }

    override fun asyncPost(
        url: String,
        params: MutableMap<String, Any>,
        callBack: IUpdateHttpService.Callback
    ) {
        //这里默认post的是Form格式，使用json格式的请修改 post -> postString
        val requestCall: RequestCall = if (isPostJson) {
            OkHttpUtils.postString()
                .url(url)
                .content(GsonUtils.toJson(params))
                .mediaType("application/json; charset=utf-8".toMediaTypeOrNull())
                .build()
        } else {
            OkHttpUtils.post()
                .url(url)
                .params(transform(params))
                .build()
        }
        requestCall.execute(object : StringCallback() {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    callBack.onError(e)
                }

                override fun onResponse(response: String, id: Int) {
                    callBack.onSuccess(response)
                }
            })
    }

    override fun download(
        url: String,
        path: String,
        fileName: String,
        callback: IUpdateHttpService.DownloadCallback
    ) {
        OkHttpUtils.get()
            .url(url)
            .tag(url)
            .build()
            .execute(object : FileCallBack(path, fileName) {
                override fun inProgress(progress: Float, total: Long, id: Int) {
                    callback.onProgress(progress, total)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    callback.onError(e)
                }

                override fun onResponse(response: File?, id: Int) {
                    callback.onSuccess(response)
                }

                override fun onBefore(request: Request?, id: Int) {
                    super.onBefore(request, id)
                    callback.onStart()
                }
            })
    }

    override fun cancelDownload(url: String) {
        OkHttpUtils.getInstance().cancelTag(url)
    }


    private fun transform(params: Map<String, Any>): Map<String, String> {
        val map: MutableMap<String, String> = TreeMap()
        for ((key, value) in params) {
            map[key] = value.toString()
        }
        return map
    }
}