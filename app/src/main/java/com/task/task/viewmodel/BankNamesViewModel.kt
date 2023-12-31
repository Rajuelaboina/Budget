package com.task.task.viewmodel

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.task.task.adapter.PaymentListAdapter
import com.task.task.database.UserDataBase
import com.task.task.model.BankNames
import com.task.task.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BankNamesViewModel: ViewModel() {
    var sum = 0
    val list = MutableLiveData<List<String>>()
    val list_bankNames = MutableLiveData<List<BankNames>>()
    val monthNamesList = MutableLiveData<List<String>>()
    val userlist = MutableLiveData<List<UserData>>()
    val list2  = MutableLiveData<List<Int>>()
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    lateinit var firebaseFireStore : FirebaseFirestore
    private var adapter = PaymentListAdapter()
    var banknameList = mutableListOf<String>()
     val listBr = MutableLiveData<List<String>>()
    fun getBankNames(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            list.postValue(UserDataBase.getInstance(context).userDao().getAllBankNames())
            //  FirebaseApp.initializeApp(context)
            /*firebaseFireStore = FirebaseFirestore.getInstance()
            firebaseFireStore.collection("BankNames").get().addOnSuccessListener {
                banknameList.clear()
                for (document in it) {
                    // val users = document.toObject(BName::class.java)
                    Log.d("RRRRRRRRRRRRRRRR<>>>>>>>>>>>>", "${document.id} => ${document.data}")

                    val bankname = document.getString("bankname")

                    banknameList.add(bankname.toString())
                  *//*  val adapter = ArrayAdapter(requireContext(),
                        R.layout.simple_spinner_item,banknameList)
                    bindingUserData.spinnerMonth.adapter = adapter
                    adapter.notifyDataSetChanged()*//*
                    *//* val ad = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,banknameList)
                     bindingUserData.autoCompleteTextView.setAdapter(ad)
                     ad.notifyDataSetChanged()*//*
                }
                listBr.postValue(banknameList)

            }*/
        }
    }
    fun getAllUsersData(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            userlist.postValue(UserDataBase.getInstance(context).userDao().getAllUserData())

        }
    }
    fun setAdapter(
        userList: List<UserData>,
        viewModel: BankNamesViewModel,
        requireActivity: FragmentActivity
    ) {
        adapter.updateList(userList,viewModel,requireActivity)
    }
    fun getAdapter(): PaymentListAdapter {
        return adapter
    }
    fun getTotal(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val total = UserDataBase.getInstance(context).userDao().getSum()
           val total2 = UserDataBase.getInstance(context).userDao().getSum2()

            val remainAmount = total-total2
            //Log.e("remainAmount","remainAmount: "+remainAmount)
            val sumTotal = UserDataBase.getInstance(context).userDao().getTotalOfSum()
            list2.postValue(listOf(remainAmount))
          //  Log.e("GRAND TOTAL","TOTAL: "+UserDataBase.getInstance(context).userDao().getTotalOfSum())

        }

    }
    fun getSumOfMonth(context: Context,name:String){

        CoroutineScope(Dispatchers.IO).launch {

            val creditotal = UserDataBase.getInstance(context).userDao().getAvailableCreditMonth(name)
            val withdrawtotal = UserDataBase.getInstance(context).userDao().getAvailableWithdrawMonth(name)
            Log.e("creditotal", "creditotal: $creditotal")
            Log.e("creditotal2", "creditotal2: $withdrawtotal")
            val availableAmount = creditotal-withdrawtotal
            Log.e("creditotal2", "creditotal2<>availableAmount: $availableAmount")
            var sum1 = UserDataBase.getInstance(context).userDao().getAvailableOfSum(name)
            monthNamesList.postValue(listOf(availableAmount.toString()))

            //sum = sum1
        }
       // Log.e("monthofsum", "someofmonth: $sum")
    }
    fun getsum(): Int{
        return sum
    }

    fun getBankNames2(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            list_bankNames.postValue(UserDataBase.getInstance(context).userDao().getBankNames())

        }
    }

}