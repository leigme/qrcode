package me.leig.baselibrary.comm

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 基础展现页面视图类
 *
 * @author leig
 * @version 20180301
 *
 */

abstract class BaseFragment constructor(protected val title: String = BaseFragment::class.java.name): Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(getLayoutId(), container, false)
        initData()
        initView(view, savedInstanceState)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        goToOther()
    }

    abstract fun getContainerId(): Int

    abstract fun getLayoutId(): Int

    abstract fun initData()

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    abstract fun goToOther()

    protected fun goToFragment(baseFragment: BaseFragment) {
        activity.fragmentManager
                .beginTransaction()
                .replace(getContainerId(), baseFragment)
                .addToBackStack(null)
                .commit()
    }

}