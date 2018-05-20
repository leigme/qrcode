package me.leig.baselibrary.view

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

class CustomWeb(context: Context, attrs: AttributeSet) : WebView(context, attrs) {

    val mProgressBar: ProgressBar = ProgressBar(context)
    val mHandler: Handler = Handler()
    lateinit var mCustomWeb: CustomWeb

    init {
        initSettings()
    }

    private fun getCustomSize(defaultSize: Int, measureSpec: Int): Int {
        var customSize = defaultSize
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        when(mode) {
            MeasureSpec.UNSPECIFIED -> {
//                customSize = defaultSize
            }
            MeasureSpec.AT_MOST -> {
                customSize = size
            }
            MeasureSpec.EXACTLY -> {
                customSize = size
            }
        }
        return customSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getCustomSize(100, widthMeasureSpec)
        val height = getCustomSize(100, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun initSettings() {
        // 初始化设置
        val mSettings = this.settings
        // 开启javascript
        mSettings.javaScriptEnabled = true
        // 开启DOM
        mSettings.domStorageEnabled = true
        // 设置字符编码
        mSettings.defaultTextEncodingName = "utf-8"
        // 设置web页面
        // 设置支持文件流
        mSettings.allowFileAccess = true
        // 支持缩放
        mSettings.setSupportZoom(true)
        // 支持缩放
        mSettings.builtInZoomControls = true
        // 调整到适合webview大小
        mSettings.useWideViewPort = true
        // 调整到适合webview大小
        mSettings.loadWithOverviewMode = true
        // 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        mSettings.defaultZoom = WebSettings.ZoomDensity.FAR
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        // 提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        mSettings.blockNetworkImage = true
        // 开启缓存机制
        mSettings.setAppCacheEnabled(true)
        webViewClient = MyWebClient()
        webChromeClient = MyWebChromeClient()
    }

    /**
     * 自定义WebChromeClient
     */
    private inner class MyWebChromeClient : WebChromeClient() {
        /**
         * 进度改变的回掉
         *
         * @param view        WebView
         * @param newProgress 新进度
         */
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            var newProgress = newProgress
            if (newProgress == 100) {
                mProgressBar.progress = 100
                // 0.2秒后隐藏进度条
                mHandler.postDelayed(runnable, 200)
            } else if (mProgressBar.visibility == View.GONE) {
                mProgressBar.visibility = View.VISIBLE
            }
            // 设置初始进度10，这样会显得效果真一点，总不能从1开始吧
            if (newProgress < 10) {
                newProgress = 10
            }
            // 不断更新进度
            mProgressBar.progress = newProgress
            super.onProgressChanged(view, newProgress)
        }
    }

    private inner class MyWebClient : WebViewClient() {
        /**
         * 加载过程中 拦截加载的地址url
         *
         * @param view
         * @param url  被拦截的url
         * @return
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            mCustomWeb.loadUrl(url)
            return true
        }

        /**
         * 页面加载过程中，加载资源回调的方法
         *
         * @param view
         * @param url
         */
        override fun onLoadResource(view: WebView, url: String) {
            super.onLoadResource(view, url)
        }

        /**
         * 页面加载完成回调的方法
         *
         * @param view
         * @param url
         */
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            // 关闭图片加载阻塞
            view.settings.blockNetworkImage = false
        }

        /**
         * 页面开始加载调用的方法
         *
         * @param view
         * @param url
         * @param favicon
         */
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            super.onReceivedError(view, errorCode, description, failingUrl)
        }

        override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
            super.onScaleChanged(view, oldScale, newScale)

        }
    }

    /**
     * 刷新界面（此处为加载完成后进度消失）
     */
    private val runnable = Runnable { mProgressBar.visibility = View.GONE }
}