package me.leig.baselibrary.comm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

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
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //隐藏标题栏
        val actionBar = supportActionBar
        //ActionBar actionBar = getActionBar();
        actionBar!!.hide()
        setContentView(getLayoutId())
        initData()
        initViews(this)
        if (null != getFragment() && 0 < getFragmentId()) {
            val ft = fragmentManager.beginTransaction()
            ft.add(getFragmentId(), getFragment()!!)
            ft.commit()
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initData()

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
}