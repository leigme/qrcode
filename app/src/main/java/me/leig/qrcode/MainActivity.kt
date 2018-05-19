package me.leig.qrcode

import me.leig.baselibrary.comm.BaseActivity
import me.leig.baselibrary.comm.BaseFragment
import me.leig.zxinglibrary.ScanManager

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getFragment(): BaseFragment? {
        return null
    }

    override fun getFragmentId(): Int {
        return 0
    }

    override fun initViews(baseActivity: BaseActivity) {
        val scanManager = ScanManager(this)
        scanManager.start()
    }

}
