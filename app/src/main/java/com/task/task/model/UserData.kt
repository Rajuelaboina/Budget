package com.task.task.model

import android.icu.util.CurrencyAmount
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "bankname")
    val bankname:String,

    @ColumnInfo(name = "creditAmount")
    val creditAmount: Double,

    @ColumnInfo(name = "withdrawAmount")
    val withdrawAmount:Double,

    @ColumnInfo(name = "availableAmount")
    val availableAmount:Double,

    @ColumnInfo(name = "date")
    val date:String
   )
