package com.example.cybermonster.jetpack.mvvm

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.cybermonster.R
import com.example.cybermonster.databinding.ActivityLoginBinding
import com.example.cybermonster.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val loginModel = LoginModel("","",this)
        binding.login = loginModel
        binding.activity = this
        setContentView(binding.root)
        fibonacci().take(10)
        var list = listOf<Int>(1,2,3,4)
        lifecycleScope.launch(Dispatchers.IO) {
            list.asFlow().collect {value ->{ print(value) }}
        }

    }
    fun fibonacci(): Sequence<Int> = sequence {
        var a = 0
        var b = 1
        yield(a)
        yield(b)

        while (true) {
            val next = a + b
            yield(next)
            a = b
            b = next
        }
    }


    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }


    fun goNext(){

    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}