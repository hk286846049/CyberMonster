package com.example.cybermonster

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyProperties
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.example.cybermonster.databinding.ActivitySpBinding
import com.example.cybermonster.utils.BiometricAuthenticationCallback
import com.example.cybermonster.utils.BiometricUt
import com.example.cybermonster.utils.StatusUtil
import com.example.cybermonster.view.TypewriterTextView
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher


class SpActivity : FragmentActivity() {
    private lateinit var binding: ActivitySpBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusUtil.setStatusBarTransparent(this@SpActivity)
        binding.tvGo.setOnClickListener {
            MainActivity.start(this@SpActivity)
        }
        binding.tvZhiwen.setOnClickListener {
            binding.lottie3.visibility = View.GONE
            binding.tvTip.setCharacterDelay(150) // 设置打字间隔（可选）
            binding.tvTip.setAnimatedText(ArrayList()) // 初始化为空列表
            binding.tvTip.addText("指纹登录模块载入..")
            val fingerprintManager =
                getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            if (!fingerprintManager.isHardwareDetected) {
                binding.tvTip.addText("设备不支持指纹识别..")
                // 硬件不支持指纹识别
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                binding.tvTip.addText("机主指纹未注册..")
                // 未注册指纹
            } else {
                binding.tvTip.addText("设备指纹识别功能正常..")
                binding.tvTip.addText("机主指纹已注册..")
                binding.tvTip.addText("等待登录..")
            }
            binding.tvTip.setLoadingCompleteListener(object :
                TypewriterTextView.LoadingCompleteListener {
                override fun onLoadingComplete() {
                    // 在此处处理加载完毕的逻辑
                    binding.lottie2.visibility = View.VISIBLE
                    binding.lottie2.setAnimation("zhiwen.json")
                    binding.lottie2.loop(true)
                    binding.lottie2.speed = 1.5f
                    binding.lottie2.playAnimation()
                    binding.lottie2.setOnClickListener {
                        BiometricUt(
                            this@SpActivity,
                            this as FragmentActivity,
                            object : BiometricAuthenticationCallback{
                                override fun onAuthenticationSuccess() {
                                    MainActivity.start(this@SpActivity)
                                }

                                override fun onAuthenticationFailure() {
                                }

                                override fun onAuthenticationError() {
                                }

                            }
                        ).showBiometricPrompt()
                    }
                }
            })
        }
        binding.tvFace.setOnClickListener {
            binding.lottie2.visibility = View.GONE
            binding.lottie3.visibility = View.VISIBLE
            binding.lottie3.setAnimation("face.json")
            binding.lottie3.loop(true)
            binding.lottie3.speed = 1.5f
            binding.lottie3.playAnimation()
            binding.tvTip.addText("从Android10开始已禁用第三方App使用人脸识别..")
        }
        binding.lottieCl.setAnimation("huoche.json")
        binding.lottieCl.loop(false)
        binding.lottieCl.speed = 2f
        binding.lottieCl.playAnimation()
        binding.lottieCl.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                // 创建渐变动画
                val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
                fadeOutAnimation.duration = 500 // 动画持续时间（毫秒）

                // 设置监听器以在动画结束后隐藏 TextView
                fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        binding.lottieCl.visibility = View.GONE
                        binding.tvTitle.visibility = View.VISIBLE
                        // 创建缩小动画
                        val scaleXDown = ObjectAnimator.ofFloat(
                            binding.tvTitle,
                            View.SCALE_X,
                            1.3f,
                            1.0f
                        )
                        val scaleYDown = ObjectAnimator.ofFloat(
                            binding.tvTitle,
                            View.SCALE_Y,
                            1.3f,
                            1.0f
                        )
                        scaleXDown.duration = 1000 // 动画持续时间（毫秒）
                        scaleYDown.duration = 1000 // 动画持续时间（毫秒）

                        // 创建放大动画
                        val scaleXUp = ObjectAnimator.ofFloat(
                            binding.tvTitle,
                            View.SCALE_X,
                            0.5f,
                            1.3f
                        )
                        val scaleYUp = ObjectAnimator.ofFloat(
                            binding.tvTitle,
                            View.SCALE_Y,
                            0.5f,
                            1.3f
                        )
                        scaleXUp.duration = 1000 // 动画持续时间（毫秒）
                        scaleYUp.duration = 1000 // 动画持续时间（毫秒）

                        // 创建 AnimatorSet 来组合两个动画
                        val scaleDownUpAnimatorSet = AnimatorSet()
                        scaleDownUpAnimatorSet.play(scaleXUp).with(scaleYUp)
                        scaleDownUpAnimatorSet.play(scaleXDown).with(scaleYDown).after(scaleXUp)

                        // 设置插值器以使动画速度均匀
                        scaleDownUpAnimatorSet.interpolator = AccelerateDecelerateInterpolator()
                        scaleDownUpAnimatorSet.start()
                        // 创建渐变动画
                        val fadeOutAnimation = AlphaAnimation(0.0f, 1.0f)
                        fadeOutAnimation.duration = 2000 // 动画持续时间（毫秒）
                        binding.ivBanner.startAnimation(fadeOutAnimation)
                        binding.ivBanner.visibility = View.VISIBLE
                        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                            }

                            @RequiresApi(Build.VERSION_CODES.M)
                            override fun onAnimationEnd(animation: Animation?) {
                                binding.tvZhiwen.visibility = View.VISIBLE
                                binding.tvFace.visibility = View.VISIBLE
                                binding.tvGo.visibility = View.VISIBLE
                                binding.view.visibility = View.VISIBLE
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                            }
                        })
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                binding.lottieCl.startAnimation(fadeOutAnimation)
//                MainActivity.start(this@SpActivity)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationStart(animation: Animator) {
            }
        })
    }
}