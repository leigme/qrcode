package me.leig.qrcode

import android.util.Log
import me.leig.baselibrary.comm.BaseApplication

/**
 *
 *
 * @author leig
 * @version 20180301
 *
 */

class App: BaseApplication() {

    private val tag = App::class.java.name

    override fun initService() {
        Log.i(tag, "初始化服务")
    }

    override fun initModule() {
        Log.i(tag, "初始化模块")
    }


}