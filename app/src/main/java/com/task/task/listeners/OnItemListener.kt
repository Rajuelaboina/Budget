package com.task.task.listeners

import com.task.task.model.UserData

interface OnItemListener {
    fun onItemClicked(userData: UserData, position: Int)
}