package com.example.cybermonster.jetpack.mvvm

import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [UserBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}