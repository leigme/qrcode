package me.leig.baselibrary.comm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * 基础视图控制器
 *
 * @author leig
 * @version 20180301
 *
 */

abstract class BaseActivity constructor(protected val title: String = BaseActivity::class.java.name): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        if (null != getFragment() && 0 < getFragmentId()) {

            val ft = fragmentManager.beginTransaction()

            ft.add(getFragmentId(), getFragment()!!)

            ft.commit()
        }

        initViews(this)

    }

    abstract fun getLayoutId(): Int

    abstract fun getFragment(): BaseFragment?

    abstract fun getFragmentId(): Int

    abstract fun initViews(baseActivity: BaseActivity)

    protected fun addFragment(baseFragment: BaseFragment) {
        if (null != getFragment() && 0 < getFragmentId()) {
            fragmentManager.beginTransaction()
                    .replace(getFragmentId(), baseFragment, null)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val fm = supportFragmentManager
        var index = requestCode shr 16
        if (0 != index) {
            index--
            if (fm.fragments == null || 0 > index || fm.fragments.size <= index) {
                Log.w(title, "Activity result fragment index out of range: 0x" + Integer.toHexString(requestCode))
                return
            }
            val frag = fm.fragments[index]
            if (frag == null) {
                Log.w(title, "Activity result no fragment exists for index: 0x" + Integer.toHexString(requestCode))
            } else {
                handleResult(frag, requestCode, resultCode, data)
            }
            return
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private fun handleResult(frag: android.support.v4.app.Fragment, requestCode: Int, resultCode: Int,
                             data: Intent) {
        frag.onActivityResult(requestCode and 0xffff, resultCode, data)
        val frags = frag.childFragmentManager.fragments
        if (frags != null) {
            for (f in frags!!) {
                if (f != null)
                    handleResult(f!!, requestCode, resultCode, data)
            }
        }
    }
}