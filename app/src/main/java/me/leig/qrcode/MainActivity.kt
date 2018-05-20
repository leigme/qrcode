package me.leig.qrcode

import android.os.Bundle
import me.leig.baselibrary.comm.BaseActivity
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getFragment(): BaseFragment? {
        val mainFragment = MainFragment()
        if (null == mainFragment.arguments) {
            val bundle = Bundle()
            mainFragment.arguments = bundle
        }
        mainFragment.arguments.putInt(Constant.CONTENT_ID, R.id.fl_container)
        return mainFragment
    }

    override fun getFragmentId(): Int {
        return R.id.fl_container
    }

    override fun initViews(baseActivity: BaseActivity) {
        addFragment(this.getFragment()!!)
    }

}
