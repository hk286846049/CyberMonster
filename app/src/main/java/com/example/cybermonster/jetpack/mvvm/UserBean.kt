package com.example.cybermonster.jetpack.mvvm

import android.location.Address
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserBean (@ColumnInfo(name = "user_account") val account: String // 账号  ColumnInfo主要用来修改在数据库中的字段名。
                     , @ColumnInfo(name = "user_pwd") val pwd: String // 密码
                     , @ColumnInfo(name = "user_name") val name: String
                     , @Embedded val address: Address // 地址  Embedded用于嵌套，里面的字段同样会存储在数据库中。
                     , @Ignore val state: Int // 状态只是临时用，所以不需要存储在数据库中
 ){
    @PrimaryKey(autoGenerate = true)  //声明该字段主键并可以声明是否自动创建。
    @ColumnInfo(name = "id")
    var id: Long = 0
}