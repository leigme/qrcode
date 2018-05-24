package me.leig.baselibrary.util

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 下载工具类
 *
 * @author leig
 * @version 20171231
 *
 */

class DownloadTool constructor(
        private val downloadCallBack: DownloadCallBack
    ): AsyncTask<String, Double, Boolean>() {

    private val tag = DownloadTool::class.java.name

    private var url = ""

    private var destPath = ""

    private var total = 0

    override fun onPreExecute() {
        downloadCallBack.downloadStart()
        Log.i(tag, "开始下载")
    }

    override fun doInBackground(vararg params: String?): Boolean {
        Log.d(tag, "doInBackground. url:{${params[0]}}, dest:{${params[1]}}")
        url = params[0]!!
        destPath = params[1]!!
        var num = 0
        if ("" != url && "" != destPath) {
            val fileDir = File(destPath).parentFile
            if (!fileDir.exists()) {
                val result = fileDir.mkdirs()
                Log.i(tag, "父级目录创建结果: $result")
            }
            var ops: FileOutputStream? = null
            var urlConnection: HttpURLConnection? = null
            var ips: InputStream? = null
            try {
                ops = FileOutputStream(destPath)
                urlConnection = URL(url).openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 15000
                urlConnection.readTimeout = 15000
                total = urlConnection.contentLength
                ips = urlConnection.inputStream
                val buffer = ByteArray(10 * 1024)
                var len = 0
                while (-1 != len) {
                    len = ips.read(buffer)
                    if (-1 == len) {
                        break
                    }
                    ops.write(buffer, 0, len)
                    num += len
                    downloadCallBack.downloading(downloading(num, total))
                }
                ops.flush()
                return true
            } catch (e: Exception) {
                Log.e(tag, "捕获异常: " + e.message)
            } finally {
                if (null != ips) {
                    ips.close()
                }
                if (null != ops) {
                    ops.close()
                }
                if (null != urlConnection) {
                    urlConnection.disconnect()
                }
            }
        }
        return false
    }

    override fun onPostExecute(result: Boolean?) {
        downloadCallBack.downloadEnd(destPath)
        Log.i(tag, "下载完成")
    }

    private fun downloading(num: Int, total: Int): Int {
        val result = num.toDouble() / total.toDouble() * 100
        return result.toInt()
    }
}