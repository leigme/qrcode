package me.leig.baselibrary.comm

import android.app.Application

/**
 * 基础启动类
 *
 * @author leig
 * @version 20180301
 *
 */

abstract class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initService()
        initModule()
    }

    abstract fun initService()

    abstract fun initModule()

}