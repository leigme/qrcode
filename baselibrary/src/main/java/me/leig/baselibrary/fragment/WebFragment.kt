package me.leig.baselibrary.fragment

import android.os.Bundle
import android.view.View
import me.leig.baselibrary.R
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant
import me.leig.baselibrary.view.CustomWebView

/**
 * 自定义WebFragment
 *
 * @author leig
 * @version 20171231
 *
 */

class WebFragment: BaseFragment(WebFragment::class.java.name) {

    override fun getContainerId(): Int {
        return arguments.getInt(Constant.CONTENT_ID)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_web
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (null != bundle) {
            val url = bundle.getString("URL")
            if (null != url && "" != url) {
                val cwc = view.findViewById(R.id.cw_content) as CustomWebView
                cwc.loadUrl(url)
            }
        }
    }

}