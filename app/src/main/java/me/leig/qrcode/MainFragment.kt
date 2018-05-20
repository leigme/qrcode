package me.leig.qrcode

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_container.view.*
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant
import me.leig.baselibrary.fragment.WebFragment
import me.leig.zxinglibrary.ScanManager

/**
 *
 *
 * @author leig
 * @version 20171231
 *
 */

class MainFragment: BaseFragment(MainFragment::class.java.name), ListItemListener, View.OnClickListener {

    private lateinit var scanManager: ScanManager

    private val dataList = mutableListOf<String>()

    override fun getContainerId(): Int {
        return arguments.getInt(Constant.CONTENT_ID)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_container
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        scanManager = ScanManager(this)
        val listAdapter = ListAdapter(activity, R.layout.recycler_list_item, dataList)
        listAdapter.listItemListener = this
        view.rv_list.adapter = listAdapter
        view.rv_list.layoutManager = LinearLayoutManager(activity)
        view.btn_scan.setOnClickListener(this)
    }

    // 回调:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val contents = scanManager.callBack(requestCode, resultCode, data)
        if (null == contents) {
            Toast.makeText(activity, "扫描失败咯...", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(activity, "扫描成功了: $contents", Toast.LENGTH_SHORT).show()
        dataList.add(contents)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_scan -> {
                scanManager.start()
            }
        }
    }

    override fun itemOnLongClickListener(view: View, position: Int) {
        Toast.makeText(activity, "长按的是: $position, 内容是: ${dataList[position]}", Toast.LENGTH_SHORT).show()
    }

    override fun itemOnClickListener(view: View, position: Int) {
        Toast.makeText(activity, "点击的是: $position, 内容是: ${dataList[position]}", Toast.LENGTH_SHORT).show()
        val web = WebFragment()
        val bundle = Bundle()
        bundle.putString("URL", dataList[position])
        web.arguments = bundle
        goToFragment(web)
    }
}