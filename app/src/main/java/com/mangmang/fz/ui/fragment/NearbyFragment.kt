package com.mangmang.fz.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.happyfi.lelerong.base.BaseFragment
import com.happyfi.lelerong.base.BaseInjectorFragment
import com.mangmang.fz.R
import com.mangmang.fz.adapter.NearbyUserAdapter
import com.mangmang.fz.base.BaseResponse
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.NearbyUser
import com.mangmang.fz.bean.PageBean
import com.mangmang.fz.net.ApiService
import com.mangmang.fz.ui.act.UserDetailActivity
import com.mangmang.fz.ui.route.Router
import com.mangmang.fz.utils.Constants
import com.mangmang.fz.utils.PermissionUtils
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.android.FragmentEvent
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration
import kotlinx.android.synthetic.main.layout_refresh.*
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
class NearbyFragment : BaseInjectorFragment(), BaseQuickAdapter.OnItemClickListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        //进入别人的空间

        val data = adapter.getData().get(position) as NearbyUser
        Router.newIntent(activity)
                .to(UserDetailActivity::class.java)
                .putString(Constants.USER_ID, data.uid)
                .launch()
    }


    private lateinit var locationClient: AMapLocationClient
    @Inject
    lateinit var apiservice: ApiService
    private var page = 1
    lateinit var adapter: NearbyUserAdapter
    var latitude: String = ""
    var longitude: String = ""

    override fun getLayoutResources(): Int {
        return R.layout.layout_refresh
    }

    override fun initView() {
        this.rootView?.background = ColorDrawable(resources.getColor(R.color.lineColor))
        adapter = NearbyUserAdapter(R.layout.item_nearby_user, mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, 4)
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration
                .Builder(activity)
                .color(resources.getColor(R.color.lineColor)).build())
        recyclerView.addItemDecoration(VerticalDividerItemDecoration
                .Builder(activity)
                .color(resources.getColor(R.color.lineColor)).build())

        adapter.setOnItemClickListener(this)
        refreshLayout.setOnRefreshListener {
            requestNearbyUser(latitude, longitude)
        }
        refreshLayout.setOnLoadmoreListener {
            page++
            requestNearbyUser(latitude, longitude)
        }
    }


    override fun initData() {
        requestLocationPermissions()
    }

    private var locationListener = AMapLocationListener { aMapLocation: AMapLocation? ->
        when (aMapLocation?.errorCode) {
            0 -> {
                //获取当前
                latitude = aMapLocation.latitude.toString()
                longitude = aMapLocation.longitude.toString()
                requestNearbyUser(latitude, longitude)
            }
            else -> {
                Toast.makeText(activity, "获取地理位置失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestNearbyUser(latitude: String, longitude: String) {
        if (page == 1) //刷新才更新地址
            apiservice.updateLocation(latitude, longitude)
                    .compose(this.bindUntilEvent(FragmentEvent.DETACH))
                    .applySchedulers()
                    .subscribe({}, {})
        apiservice.getNearUsers(latitude, longitude, this@NearbyFragment.page)
                .compose(this.bindUntilEvent(FragmentEvent.DETACH))
                .applySchedulers()
                .subscribe({
                    refreshLayout.finishLoadmore()
                    refreshLayout.finishRefresh()
                    if (it.status == Constants.REQUEST_SUCCESS) {
                        if (page == 1)
                            adapter.replaceData(it.data!!.list)
                        else
                            adapter.addData(it.data!!.list)
                    } else
                        showToast("加载失败")

                }, {
                    showToast(it.message)
                })
    }


    private fun requestLocationPermissions() {
        RxPermissions(activity)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({
                    when {
                        it.granted && PermissionUtils.checkPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                            locationClient = AMapLocationClient(activity)
                            locationClient?.setLocationListener(locationListener)
                            val locationOption = AMapLocationClientOption()
                            locationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                            locationOption.setOnceLocation(true)
                            locationOption.setMockEnable(false)
                            locationOption.setHttpTimeOut(10000)
                            locationClient?.setLocationOption(locationOption)
                            locationClient?.startLocation()
                        }
                        else -> {
                            AlertDialog.Builder(activity)
                                    .setMessage("请在手机设置-应用-乐乐融-权限中点击允许开启定位位置权限，以便正常使用乐乐融功能。")
                                    .setPositiveButton("去设置") { dialog, _ ->
                                        PermissionUtils.goSetting(activity)
                                        dialog.dismiss()
                                    }
                                    .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                                    .show()
                        }
                    }
                }, {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                })
    }


}