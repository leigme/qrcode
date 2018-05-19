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

        val fragment = inflater!!.inflate(getLayoutId(), container, false)

        initViews(fragment, savedInstanceState)

        return fragment

    }

    abstract fun getLayoutId(): Int

    abstract fun initViews(view: View, savedInstanceState: Bundle?)

    protected fun goToFragment(baseFragment: BaseFragment) {
//        val bundle = baseFragment.arguments

    }

}