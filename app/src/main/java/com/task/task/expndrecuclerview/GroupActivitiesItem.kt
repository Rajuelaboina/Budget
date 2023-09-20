package com.phycae.recyclerviewitemex

data class GroupActivitiesItem(
    val ActivityGroupId: Int,
    val CompOrNonCompActivity: Int,
    val Description: String,
    val GroupName: String,
    val Id: Int,
    val IsDel: Boolean,
    val Message: String,
    val TypeOfActivity: String,
    val order: Int
)