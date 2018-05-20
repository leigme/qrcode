package me.leig.zxinglibrary

import android.app.Fragment
import android.content.Intent
import com.google.zxing.integration.android.IntentIntegrator

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

open class ScanManager constructor(fragment: Fragment) {

    private val integrator = IntentIntegrator.forFragment(fragment)

    init {
        initScan()
    }

    private fun initScan() {
        // 底部的提示文字，设为""可以置空
        integrator.setPrompt("请扫描")
        // 前置或者后置摄像头
        integrator.setCameraId(0)
        // 扫描成功的「哔哔」声，默认开启
        integrator.setBeepEnabled(false)
        // 设置扫描activity，也就是上面写的Activity
        integrator.captureActivity = ScanActivity::class.java
    }

    fun start() {
        integrator.initiateScan()
    }

    fun callBack(requestCode: Int, resultCode: Int, data: Intent): String? {
        val resultIntent = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        return if (null != resultIntent) {
            if (null == resultIntent.contents) {
                null
            } else {
                resultIntent.contents
            }
        } else {
            null
        }
    }


}