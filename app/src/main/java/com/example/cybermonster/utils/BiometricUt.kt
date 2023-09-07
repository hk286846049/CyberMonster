package com.example.cybermonster.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class BiometricUt(
    val context: Context,
    val activity: FragmentActivity,
    val authenticationCallback: BiometricAuthenticationCallback
) {

    // 注册事件监听者与执行器
    val handler = Handler()
    val executor = Executor {
        handler.post(it)
    }

    // 拉取人脸识别窗口
    open fun showBiometricPrompt() {

        // 配置人脸识别窗口的提示信息
        var propertyInfo = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle("指纹识别验证")
            setSubtitle("请使用您的指纹特征验证")
            setNegativeButtonText("取消")
        }.build()

        // 传入识别事件处理回调函数后，即可正常进入启动人脸识别流程
        var biometricPrompt = BiometricPrompt(activity, executor, auth())
        biometricPrompt.authenticate(propertyInfo)
    }

    // 使用一个内部类实现识别回调函数（因为他是一个抽象函数）
    // 需要实现三个方法，分别是识别错误、成功、失败的执行方法
    inner class auth : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(context, "识别错误", Toast.LENGTH_SHORT).show()
            authenticationCallback.onAuthenticationError()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Toast.makeText(context, "认证成功", Toast.LENGTH_SHORT).show()
            authenticationCallback.onAuthenticationSuccess()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(context, "认证失败", Toast.LENGTH_SHORT).show()
            authenticationCallback.onAuthenticationFailure()
        }
    }
}
interface BiometricAuthenticationCallback {
    fun onAuthenticationSuccess()
    fun onAuthenticationFailure()
    fun onAuthenticationError()
}
