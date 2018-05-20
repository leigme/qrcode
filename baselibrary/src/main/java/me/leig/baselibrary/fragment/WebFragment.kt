package me.leig.baselibrary.fragment

import android.os.Bundle
import android.view.View
import me.leig.baselibrary.R
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.view.CustomWeb

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

class WebFragment: BaseFragment(WebFragment::class.java.name) {

    override fun getLayoutId(): Int {
        return R.layout.fragment_web
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val url = bundle.getString("URL")
        val cwc = view.findViewById(R.id.cw_content) as CustomWeb
        cwc.loadUrl(url)
    }

}