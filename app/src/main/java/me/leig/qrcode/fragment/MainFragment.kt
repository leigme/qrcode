package me.leig.qrcode.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_container.view.*
import me.leig.baselibrary.comm.BaseFragment
import me.leig.baselibrary.comm.Constant
import me.leig.qrcode.R
import me.leig.qrcode.adapter.ListAdapter
import me.leig.qrcode.adapter.ListItemListener
import me.leig.zxinglibrary.ScanManager
import java.io.File

/**
 * 主列表视图
 *
 * @author leig
 * @version 20171231
 *
 */

class MainFragment: BaseFragment(MainFragment::class.java.name), View.OnTouchListener, ListItemListener, View.OnClickListener {

    private lateinit var scanManager: ScanManager

    private lateinit var listAdapter: ListAdapter

    private var dataList = mutableListOf<String>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }

    override fun getContainerId(): Int {
        return arguments.getInt(Constant.CONTENT_ID)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_container
    }

    override fun initData() {
        val file = File(activity.applicationContext.filesDir, "contents.json")
        if (file.exists()) {
            val clazz = object : TypeToken<MutableList<String>>() {}.type
            dataList = Gson().fromJson(file.readText(), clazz)
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        scanManager = ScanManager(this)
        listAdapter = ListAdapter(activity, R.layout.recycler_list_item, dataList)
        listAdapter.listItemListener = this
        view.rv_list.adapter = listAdapter
        view.rv_list.layoutManager = LinearLayoutManager(activity)
        view.btn_scan.setOnClickListener(this)
        if (0 == dataList.size) {
            scanManager.start()
        }
    }

    // 回调:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (null == data) {
            return
        }
        val contents = scanManager.callBack(requestCode, resultCode, data)
        if (null == contents) {
            Toast.makeText(activity, "扫描失败咯...", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(activity, "扫描成功了: $contents", Toast.LENGTH_SHORT).show()
        saveData(contents)
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

    private fun saveData(contents: String) {
        dataList.add(contents)
        val json = Gson().toJson(dataList)
        val file = File(activity.applicationContext.filesDir, "contents.json")
        file.writeText(json)
        listAdapter.setDataList(dataList)
    }
}