package com.example.cybermonster.jetpack.mvvm

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.room.Room
import com.example.cybermonster.MainActivity

class LoginModel constructor(name:String,pass:String,context:Context){
    val n = ObservableField<String>(name) // ObservableField,构造函数，设置可观察的域
    val p = ObservableField<String>(pass)
    val context = context
    // Room 数据库
    private lateinit var db: AppDatabase

    init {
        // 初始化 Room 数据库
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "user-database"
        ).build()
    }

    /**
     * 用户名改变回调的函数
     */
    fun onNameChanged(s: CharSequence) {
        n.set(s.toString())
    }
    /**
     * 密码改变的回调函数
     */
    fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        p.set(s.toString())
    }
    fun toLogin() {
        if (n.get().equals("aaa")
            && p.get().equals("vvv")
        ) {
            Toast.makeText(context, "账号密码正确", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
    fun isLogin(): Boolean {
        // 查询用户数据
        val user = db.userDao().getUserByNameAndPassword(n.get(), p.get())
        return user != null
        return (n.get().equals("aaa")
                && p.get().equals("vvv"))

    }
}