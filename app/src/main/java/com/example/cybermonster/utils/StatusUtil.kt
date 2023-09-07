package com.example.cybermonster.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout


class StatusUtil {
    companion object{
        fun setStatusBarTransparent(activity: Activity) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return
            }
            // 实现功能
            val window: Window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //        statusBarColor = window.getStatusBarColor();
            window.setStatusBarColor(Color.TRANSPARENT)
        }

    }
    // 全局保存状态栏颜色
    private val statusBarColor: Int = Color.BLACK

    // 全局保存状态栏高度
    private val statusBarHeight = 0

    // 全局保存自己绘制的状态栏View
    private var statusView: View? = null

    /**
     * 使状态栏透明并且兼容有虚拟按键的手机
     */

    fun clearStatusBarTransparency(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        // 实现功能
        val window: Window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(statusBarColor)
    }


    /**
     * 为布局文件中新增的状态栏布局设置背景色和高度
     */
    fun setStatusViewAttr(view: View?, activity: Activity?) {
        if (view == null || activity == null) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        val layoutParams: ViewGroup.LayoutParams = view.getLayoutParams()
        layoutParams.height = getStatusBarHeight(activity)
        view.setLayoutParams(layoutParams)
        view.setBackgroundColor(getStatusBarColor(activity))
    }

    /**
     * 绘制一个和状态栏一样高的View，并添加到decorView中
     */
    fun createStatusView(activity: Activity?) {
        if (activity == null) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        // 绘制一个和状态栏一样高的View
        statusView = View(activity)
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity))
        statusView!!.setLayoutParams(params)
        // 设置背景色
        statusView!!.setBackgroundColor(getStatusBarColor(activity))
        // 添加 statusView 到布局中
        val decorView = activity.window.decorView as ViewGroup
        decorView.addView(statusView)
    }

    /**
     * 是否显示沉浸式效果
     * @param activity 上下文
     * @param hide true 沉浸式 false 非沉浸式
     */
    fun hideStatusView(activity: Activity, hide: Boolean) {
        if (statusView == null) {
            return
        }
        val decorView = activity.window.decorView as ViewGroup
        val firstView: View = decorView.getChildAt(0)
        val firstParams = firstView.getLayoutParams() as FrameLayout.LayoutParams
        if (hide) {
            firstParams.topMargin = 0
            statusView!!.setVisibility(View.GONE)
        } else {
            firstParams.topMargin = getStatusBarHeight(activity)
            statusView!!.setVisibility(View.VISIBLE)
        }
        firstView.setLayoutParams(firstParams)
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context?): Int {
        if (context == null || context.getResources() == null) {
            return 0
        }
        if (statusBarHeight != 0) {
            return statusBarHeight
        }
        val resourceId: Int = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId == 0) {
            0
        } else context.getResources().getDimensionPixelSize(resourceId)
    }

    /**
     * 获取状态栏颜色
     */
    fun getStatusBarColor(activity: Activity?): Int {
        if (activity == null || activity.window == null) {
            return Color.BLACK
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Color.BLACK
        }
        if (statusBarColor == Color.BLACK) {
            return activity.window.statusBarColor
        }
        return if (statusBarColor == Color.TRANSPARENT) {
            Color.BLACK
        } else statusBarColor
    }


    /**
     * 设置状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun setTranslucentStatus(activity: Activity) {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            //清除透明状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            //设置状态栏颜色必须添加
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT) //设置透明
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //19
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}