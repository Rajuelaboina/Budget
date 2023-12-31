package com.task.task

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.task.task.database.UserDataBase
import com.task.task.databinding.ActivityMainBinding
import com.task.task.databinding.DialogBankBinding
import com.task.task.model.BankNames
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var bindingBank: DialogBankBinding
   /* private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    lateinit var pushId :String */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
       getSupportActionBar()?.setDisplayShowHomeEnabled(true)
       getSupportActionBar()?.setLogo(R.drawable.ic_logo1)
       getSupportActionBar()?.setDisplayUseLogoEnabled(true)


       /* firebaseDatabase = FirebaseDatabase.getInstance("https://task-206a6-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseReference=  firebaseDatabase.reference.child("BankExpenses")
*/


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
         when (item.itemId) {
            R.id.action_settings ->  showUserDialog()


             R.id.action_delete -> startActivity(Intent(this,SettingActivity::class.java))
        }
        return true
    }
      // add the bank name
    private fun showUserDialog() {
        val dialog = BottomSheetDialog(this@MainActivity,R.style.AppBottomSheetDialogTheme)
        bindingBank = DialogBankBinding.inflate(layoutInflater)
        dialog.setContentView(bindingBank.root)
        bindingBank.saveBankName.setOnClickListener {
            if (bindingBank.editTextBankName.text.toString().trim().isNotEmpty()) {
                CoroutineScope(IO).launch {
                    val boolean = UserDataBase.getInstance(applicationContext).userDao().isBankExists(bindingBank.editTextBankName.text.toString().trim())
                    if (!boolean){
                        UserDataBase.getInstance(applicationContext).userDao().insertBank(
                            BankNames(0, bindingBank.editTextBankName.text.toString().trim()) )
                        // pushId = databaseReference.push().getKey()!!
                        // databaseReference.child("BankNames").child(pushId).child("bankName").setValue(bindingBank.editTextBankName.text.toString().trim())
                        bindingBank.editTextBankName.setText("")

                    }else{
                        showToast()

                    }

                }
            }else{
                Toast.makeText(applicationContext,"field is not empty",Toast.LENGTH_LONG).show()
            }

        }
        dialog.show()
    }

    private fun showToast() {
        runOnUiThread {
            Toast.makeText(applicationContext,"name already exist",Toast.LENGTH_LONG).show()


        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}