package me.leig.zxinglibrary

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * 二维码扫描视图
 *
 * @author leig
 * @version 20171231
 *
 */

class ScanActivity: AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    lateinit var captureManager: CaptureManager

    var isLightOn = false

    lateinit var mDBV: DecoratedBarcodeView

    lateinit var switchLight: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_scan)

        switchLight = findViewById(R.id.btn_switch) as Button

        mDBV = findViewById(R.id.dbv_custom) as DecoratedBarcodeView

        mDBV.setTorchListener(this)

        if (!hasFlash()) {
            switchLight.visibility = View.GONE
        }

        switchLight.setOnClickListener {

            if (isLightOn) {
                mDBV.setTorchOff()
            } else {
                mDBV.setTorchOn()
            }

        }

        captureManager = CaptureManager(this, mDBV)

        captureManager.initializeFromIntent(intent, savedInstanceState)

        captureManager.decode()

    }

    override fun onTorchOn() {
        Toast.makeText(this,"torch on", Toast.LENGTH_LONG).show()
        isLightOn = true
    }

    override fun onTorchOff() {
        Toast.makeText(this,"torch off",Toast.LENGTH_LONG).show()
        isLightOn = false
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        captureManager.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

}