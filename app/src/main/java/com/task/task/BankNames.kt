package com.task.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankNames(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id:Int,
    @ColumnInfo(name = "BankName")
    val name: String
    )