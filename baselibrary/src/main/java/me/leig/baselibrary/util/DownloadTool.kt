package me.leig.baselibrary.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.webkit.MimeTypeMap
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
        private val context: Context,
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
        if ("" != url && "" != destPath) {
            val fileDir = File(destPath).parentFile
            if (!fileDir.exists()) {
                fileDir.mkdirs()
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
                    ops.write(buffer, 0, len)
                    downloadCallBack.downloading(total, len)
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
        if ("application/vnd.android.package-archive" == getMIMEType(url)) {
            install(context, url)
        }
        Log.i(tag, "下载完成")
    }

    private fun getMIMEType(url: String): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (null != extension) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return ""
    }

    /**
     * 安装app
     *
     * @param path
     */
    private fun install(context: Context, path: String) {
        val file = File(path)
        if (file.exists() && !file.isDirectory) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}