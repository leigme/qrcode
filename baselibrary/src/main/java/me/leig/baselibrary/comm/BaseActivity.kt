package me.leig.baselibrary.comm

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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

}