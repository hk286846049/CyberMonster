package com.example.cybermonster

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.cybermonster.databinding.ActivityMainBinding
import com.example.cybermonster.utils.AnimalUt
import com.example.cybermonster.utils.StatusUtil
import com.example.cybermonster.view.ViewPagerAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var index = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusUtil.setStatusBarTransparent(this@MainActivity)
        AnimalUt.startInfiniteRotation(binding.ivA, 2000L)
        with(binding) {
            tvA.setOnClickListener(this@MainActivity)
            tvB.setOnClickListener(this@MainActivity)
            tvC.setOnClickListener(this@MainActivity)
            ivA.setOnClickListener(this@MainActivity)
            ivA.setOnClickListener(this@MainActivity)
            ivA.setOnClickListener(this@MainActivity)
            val fragmentList = listOf(HomeFragment())
            val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
            viewpager.adapter = adapter
            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_a, R.id.tv_a -> {
                if (index == 0) {
                    return
                }
            }
            R.id.iv_b, R.id.tv_b -> {
                if (index == 1) {
                    return
                }
            }
            R.id.iv_c, R.id.tv_c -> {
                if (index == 2) {
                    return
                }
            }
        }
        binding.ivA.clearAnimation()
        binding.ivB.clearAnimation()
        binding.ivC.clearAnimation()
        binding.tvA.setTextColor(resources.getColor(R.color.white))
        binding.tvB.setTextColor(resources.getColor(R.color.white))
        binding.tvC.setTextColor(resources.getColor(R.color.white))
        when (v?.id) {
            R.id.iv_a, R.id.tv_a -> {
                index =0
                AnimalUt.startInfiniteRotation(binding.ivA, 2000L)
                binding.tvA.setTextColor(resources.getColor(R.color.color_4158D0))
            }
            R.id.iv_b, R.id.tv_b -> {
                index =1
                AnimalUt.startHorizontalShake(binding.ivB, 4000L,20f)
                binding.tvB.setTextColor(resources.getColor(R.color.color_4158D0))

            }
            R.id.iv_c, R.id.tv_c -> {
                index =2
                AnimalUt.startMoveFromBottomToTop(binding.ivC, 4000L)
                binding.tvC.setTextColor(resources.getColor(R.color.color_4158D0))

            }
        }

    }
}