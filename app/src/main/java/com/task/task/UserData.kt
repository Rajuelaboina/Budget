package com.task.task

import android.icu.util.CurrencyAmount
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "creditAmount")
    val creditAmount: Double,

    @ColumnInfo(name = "depositAmount")
    val depositAmount:Double,

    @ColumnInfo(name = "bankname")
    val bankname:String,

    @ColumnInfo(name = "date")
    val date:String
   )
