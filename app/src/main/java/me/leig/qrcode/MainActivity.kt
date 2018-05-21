package me.leig.qrcode

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import me.leig.baselibrary.comm.BaseActivity
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant
import me.leig.qrcode.fragment.MainFragment

class MainActivity : BaseActivity() {

    private val permissions = arrayOf("android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        verifyPermissions(this)
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

    private fun verifyPermissions(activity: Activity) {
        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, permissions, 1001)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
