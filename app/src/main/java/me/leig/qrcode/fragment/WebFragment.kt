package me.leig.qrcode.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ProgressBar
import me.leig.baselibrary.R
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant
import me.leig.baselibrary.util.DownloadCallBack
import me.leig.baselibrary.view.CustomWebView
import java.io.File

/**
 * 自定义内部网页
 *
 * @author leig
 * @version 20180301
 *
 */

class WebFragment: BaseFragment(WebFragment::class.java.name), DownloadCallBack {

    private var mProgressBar: ProgressBar? = null

    override fun getContainerId(): Int {
        return arguments.getInt(Constant.CONTENT_ID)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_web
    }

    override fun initData() {
        Log.i(tag, "初始化数据")
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (null != bundle) {
            val url = bundle.getString("URL")
            if (null != url && "" != url) {
                val cwc = view.findViewById(R.id.cw_content) as CustomWebView
                cwc.loadUrl(url)
                val filePath = Environment.getExternalStorageDirectory().absolutePath + "/qrcode/"
                cwc.filePath = filePath
                cwc.downloadCallBack = this
                mProgressBar = view.findViewById(R.id.pb_download) as ProgressBar
            }
        }
    }

    override fun downloadStart() {
        mProgressBar!!.visibility = View.VISIBLE
        Log.i(tag, "下载开始咯...")
    }

    override fun downloading(num: Int, total: Int) {
        mProgressBar!!.progress = num / total
        Log.d(tag, "下载进行中: $num / $total")
    }

    override fun downloadEnd(path: String) {
        mProgressBar!!.visibility = View.GONE
        if ("application/vnd.android.package-archive" == getMIMEType(path)) {
            install(activity, path)
        }
        Log.i(tag, "下载结束了: $path")
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
            if (24 <= Build.VERSION.SDK_INT) {
                val apkUri = FileProvider.getUriForFile(context, "me.leig.qrcode", file)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            }
            context.startActivity(intent)
        }
    }
}