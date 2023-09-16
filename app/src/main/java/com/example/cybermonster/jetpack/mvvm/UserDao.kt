package com.example.cybermonster.jetpack.mvvm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserBean)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserBean>
}