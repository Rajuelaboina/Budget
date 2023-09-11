package com.task.task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [BankNames::class,UserData::class], version = 1)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        var instance: UserDataBase?=null
        fun getInstance(context: Context):UserDataBase{
            if (instance==null){
               instance = Room.databaseBuilder(context.applicationContext,UserDataBase::class.java,"UserData")
                   .fallbackToDestructiveMigration()
                   .build()
            }
            return instance!!
        }
    }
}