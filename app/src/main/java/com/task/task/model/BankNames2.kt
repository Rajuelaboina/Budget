package com.task.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankNames2(
    @ColumnInfo(name = "BankName")
    val name: String
    )