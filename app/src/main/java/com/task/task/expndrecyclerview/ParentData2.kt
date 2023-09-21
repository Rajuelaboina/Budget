package com.phycae.recyclerviewitemex

import com.task.task.Constants

data class ParentData2(
    val parentTitle:String?=null,
    var type:Int = Constants.PARENT,
    var subList : MutableList<GroupActivitiesItem> = ArrayList(),
    var isExpanded:Boolean = false
)