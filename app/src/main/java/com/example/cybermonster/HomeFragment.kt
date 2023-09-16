package com.example.cybermonster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.cybermonster.adapter.HomeBannerAdapter
import com.example.cybermonster.databinding.FragmentHomeBinding
import com.youth.banner.indicator.RoundLinesIndicator


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            //
            Glide.with(this@HomeFragment)
                .load(resources.getDrawable(R.drawable.head_dog))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(ivHead)
            val intList: List<Int> = listOf(R.drawable.banner1, R.drawable.banner2)
            topBanner.addBannerLifecycleObserver(this@HomeFragment)
            topBanner.indicator = RoundLinesIndicator(context)
            topBanner.setIndicatorSpace(0)
            topBanner.setLoopTime(3000)
            topBanner.setAdapter(context?.let { HomeBannerAdapter(it,intList) })

        }
    }


    companion object {
        fun newInstance(): HomeFragment? {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}