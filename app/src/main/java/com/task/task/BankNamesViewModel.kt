package com.task.task

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BankNamesViewModel: ViewModel() {
    val list = MutableLiveData<List<BankNames>>()
    val userlist = MutableLiveData<List<UserData>>()
    val list2  = MutableLiveData<List<Int>>()

    private var adapter = PaymentListAdapter()
    fun getBankNames(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            list.postValue(UserDataBase.getInstance(context).userDao().getAllBankNames())
        }
    }
    fun getAllUsersData(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            userlist.postValue(UserDataBase.getInstance(context).userDao().getAllUserData())

        }
    }
    fun setAdapter(userList: List<UserData>) {
        adapter.updateList(userList)
    }
    fun getAdapter(): PaymentListAdapter {
        return adapter
    }
    fun getTotal(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
           // val total = UserDataBase.getInstance(context).userDao().getSum()
           // val total2 = UserDataBase.getInstance(context).userDao().getSum2()

           // val sumTotal = total+total2
            val sumTotal = UserDataBase.getInstance(context).userDao().getTotalOfSum()
            list2.postValue(listOf(sumTotal))
          //  Log.e("GRAND TOTAL","TOTAL: "+UserDataBase.getInstance(context).userDao().getTotalOfSum())
        }

    }

}