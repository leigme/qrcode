package me.leig.zxinglibrary

import android.app.Activity
import com.google.zxing.integration.android.IntentIntegrator

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

open class ScanManager constructor(activity: Activity) {

    val integrator = IntentIntegrator(activity)

    init {
        initScan()
    }

    private fun initScan() {
        integrator.setPrompt("请扫描") //底部的提示文字，设为""可以置空
        integrator.setCameraId(0) //前置或者后置摄像头
        integrator.setBeepEnabled(false) //扫描成功的「哔哔」声，默认开启
        integrator.captureActivity = ScanActivity::class.java //设置扫描activity，也就是上面写的Activity
    }

    fun start() {
        integrator.initiateScan()
    }
}