package com.task.task

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.task.task.database.UserDataBase
import com.task.task.databinding.ActivitySettingBinding
import com.task.task.model.BankNames
import com.task.task.viewmodel.BankNamesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {
    lateinit var binding:ActivitySettingBinding
    private lateinit var viewModel: BankNamesViewModel
    var list = ArrayList<BankNames>()
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_setting)
        viewModel = ViewModelProvider(this)[BankNamesViewModel::class.java]
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAdapter()


        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val alertDialog =AlertDialog.Builder(this@SettingActivity)
            alertDialog.setTitle("Do you want delete name")
            alertDialog.setCancelable(false).setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {



                        CoroutineScope(Dispatchers.IO).launch {
                            val name = parent.getItemIdAtPosition(position).toString()
                            UserDataBase.getInstance(applicationContext).userDao().deleteBank(list[position].id.toString())

                        }




                        showAdapter()
                    }

                })
            alertDialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    alertDialog.setCancelable(true)
                }

            })
            alertDialog.show()
        }

    }

    private fun showAdapter() {
        viewModel.getBankNames2(applicationContext)
        viewModel.getBankNames(applicationContext)
        viewModel.list.observe(this){
            val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,it)
            binding.listView.adapter = adapter
        }
        viewModel.list_bankNames.observe(this){
            list = it as ArrayList<BankNames>
        }
    }
}