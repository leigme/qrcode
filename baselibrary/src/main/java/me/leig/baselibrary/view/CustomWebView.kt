package me.leig.baselibrary.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import me.leig.baselibrary.R

/**
 * 自定义WebView
 *
 * @author leig
 * @version 20171231
 *
 */

class CustomWebView  : RelativeLayout, DownloadListener {

    var mContext: Context

    lateinit var mWebView: WebView

    lateinit var mProgressBar: ProgressBar

    constructor(context: Context): super(context) {
        this.mContext = context
        initView()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.mContext = context
        initView()
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        this.mContext = context
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(mContext).inflate(R.layout.relativelayout_web, this)
        mWebView = view.findViewById(R.id.web_view)
        mProgressBar = view.findViewById(R.id.progress_bar)
        initWebViewSet()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSet() {
        // 设置编码
        mWebView.settings.defaultTextEncodingName = "utf-8"
        mWebView.settings.textZoom = 70
        // 设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0))
        // 设置可以支持缩放
        mWebView.settings.setSupportZoom(true)
        // 设置缓存模式
        mWebView.settings.setAppCacheEnabled(true)
        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        // //添加Javascript调用java对象
        mWebView.settings.javaScriptEnabled = true
        // 设置出现缩放工具
        mWebView.settings.builtInZoomControls = true
        mWebView.settings.displayZoomControls = false
        // 扩大比例的缩放设置此属性，可任意比例缩放。
        mWebView.settings.loadWithOverviewMode = true
        mWebView.settings.blockNetworkImage = false
        // 不启用硬件加速
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        // 自适应屏幕
        mWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

        mWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_LONG).show()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mProgressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mProgressBar.visibility = View.GONE
            }
        }

        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mProgressBar.progress = newProgress
            }
        }

        mWebView.setDownloadListener(this)
    }

    override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long) {
        Toast.makeText(mContext, "点击下载咯...[$url]", Toast.LENGTH_SHORT).show()
    }

    fun loadUrl(url: String) {
        mWebView.loadUrl(url)
    }
}