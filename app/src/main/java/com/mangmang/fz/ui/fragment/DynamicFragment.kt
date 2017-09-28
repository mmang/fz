package com.mangmang.fz.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.mangmang.fz.R
import com.mangmang.fz.adapter.DynamicAdapter
import com.mangmang.fz.adapter.base.BaseQuickAdapter
import com.mangmang.fz.bean.Dynamic
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.ui.act.UserDetailActivity
import com.mangmang.fz.ui.contract.DynamicContract
import com.mangmang.fz.ui.presenter.DynamicP
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.showToast
import kotlinx.android.synthetic.main.fragment_dynamic.*

/**
 * Created by mangmang on 2017/9/19.
 */
class DynamicFragment : HasPFragment<DynamicP>(), DynamicContract.V, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        showToast(view.id.toString())
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.getData()?.get(position) as DynamicItem
        when (view.id) {
            R.id.avatar -> Router.newIntent(activity).to(UserDetailActivity::class.java).putString(Constants.USER_ID, data.uid).launch()
        }
    }

    private var pageIndex = 1
    private var dynamicAdapter = lazy {
        DynamicAdapter(R.layout.item_dynamic, mutableListOf())
    }

    override fun setData(dynamic: Dynamic) {
        dynamicAdapter.value.addData(dynamic.data.list)
    }

    override fun initView() {
        presenter.loadDynamicData(pageIndex)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = dynamicAdapter.value

        dynamicAdapter.value.setOnItemChildClickListener(this)

    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_dynamic
    }


    override fun onFragmentVisiableChange(b: Boolean) {
        Log.d("fragment", b.toString())
        if (b) {
            //第一次加载
        }
    }
}