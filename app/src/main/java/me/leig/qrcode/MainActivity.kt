package me.leig.qrcode

import me.leig.baselibrary.comm.BaseActivity
import me.leig.baselibrary.comm.BaseFragment
import me.leig.qrcode.fragment.ZXingFragment

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getFragmentId(): Int {
        return R.id.fragment_container
    }

    override fun getFragment(): BaseFragment? {
        return ZXingFragment()
    }
}
