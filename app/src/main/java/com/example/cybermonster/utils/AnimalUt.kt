package com.example.cybermonster.utils

import android.view.animation.*
import android.widget.ImageView

class AnimalUt {
    companion object{
        fun startInfiniteRotation(imageView: ImageView, durationMillis: Long) {
            val rotateAnimation = RotateAnimation(
                0f, 360f, // 从0度旋转到360度，即一周
                Animation.RELATIVE_TO_SELF, 0.5f, // 旋转中心点的X坐标，这里是图片中心
                Animation.RELATIVE_TO_SELF, 0.5f // 旋转中心点的Y坐标，这里是图片中心
            )
            rotateAnimation.duration = durationMillis
            rotateAnimation.interpolator = LinearInterpolator() // 使用线性插值器，让动画匀速旋转
            rotateAnimation.repeatCount = Animation.INFINITE // 设置重复次数为无限
            imageView.startAnimation(rotateAnimation) // 启动动画
        }
        fun startHorizontalShake(imageView: ImageView, durationMillis: Long, shakeDistance: Float) {
            val shakeAnimation = TranslateAnimation(0f, shakeDistance, 0f, 0f)
            shakeAnimation.duration = durationMillis / 2 // 单程持续时间为总持续时间的一半
            shakeAnimation.interpolator = CycleInterpolator(1f) // 使用循环插值器，使动画来回晃动
            shakeAnimation.repeatCount = Animation.INFINITE // 设置重复次数为无限
            imageView.startAnimation(shakeAnimation)
        }
        fun startMoveFromBottomToTop(imageView: ImageView, durationMillis: Long) {
            val imageViewHeight = imageView.height
            val moveDistance = imageViewHeight/2+30

            val moveAnimation = TranslateAnimation(
                0f, 0f, // X 轴不移动
                moveDistance.toFloat(), -moveDistance.toFloat() // 从底部移动到顶部
            )
            moveAnimation.duration = durationMillis
            moveAnimation.repeatCount = Animation.INFINITE // 设置重复次数为无限

            imageView.startAnimation(moveAnimation)
        }
    }

}