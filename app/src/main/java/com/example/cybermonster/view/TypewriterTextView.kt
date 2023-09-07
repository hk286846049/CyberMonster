package com.example.cybermonster.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import java.util.*


class TypewriterTextView : androidx.appcompat.widget.AppCompatTextView {
    private var mTextList: MutableList<CharSequence> = ArrayList()
    private var mCurrentTextIndex = 0
    private var mIndex = 0
    private var mDelay: Long = 150 // 打字间隔，默认是150毫秒
    private val mHandler = Handler()
    private val mTextRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mCurrentTextIndex < mTextList.size) {
                text = mTextList[mCurrentTextIndex].subSequence(0, mIndex++)
                if (mIndex <= mTextList[mCurrentTextIndex].length) {
                    mHandler.postDelayed(this, mDelay)
                } else {
                    mIndex = 0
                    mCurrentTextIndex++
                    if (mCurrentTextIndex < mTextList.size) {
                        mHandler.postDelayed(this, mDelay)
                    } else {
                        mLoadingCompleteListener?.onLoadingComplete()
                    }
                }
            } else {
                text = ""
            }
        }
    }

    // 定义回调接口
    interface LoadingCompleteListener {
        fun onLoadingComplete()
    }

    private var mLoadingCompleteListener: LoadingCompleteListener? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    fun setCharacterDelay(millis: Long) {
        mDelay = millis
    }

    fun setAnimatedText(textList: MutableList<CharSequence>) {
        mTextList = textList
        mCurrentTextIndex = 0
        mIndex = 0
        text = ""
        mHandler.removeCallbacks(mTextRunnable)
        mHandler.postDelayed(mTextRunnable, mDelay)
    }

    fun addText(tv: CharSequence) {
        mTextList.add(tv)
        if (mCurrentTextIndex >= mTextList.size - 1) {
            mCurrentTextIndex = mTextList.size - 1
        }
        mIndex = 0
        text = ""
        mHandler.removeCallbacks(mTextRunnable)
        mHandler.postDelayed(mTextRunnable, mDelay)
    }

    // 设置加载完毕监听器
    fun setLoadingCompleteListener(listener: LoadingCompleteListener) {
        mLoadingCompleteListener = listener
    }
}

