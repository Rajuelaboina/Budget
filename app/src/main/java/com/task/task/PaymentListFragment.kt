package com.task.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.task.task.databinding.DialogUserdataBinding
import com.task.task.databinding.FragmentPaymentlistBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PaymentListFragment : Fragment(), OnItemListener {

    private var _binding: FragmentPaymentlistBinding? = null
    private lateinit var viewModel: BankNamesViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    lateinit var bindingUserData: DialogUserdataBinding
    var date = ""
    var year = 0
    var month = 0
    var day = 0
    var bankName = ""
    var paymentList = ArrayList<UserData>()
    var banknameList = mutableListOf<String>()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this)[BankNamesViewModel::class.java]
        _binding = FragmentPaymentlistBinding.inflate(inflater, container, false)
        binding.viewModel =viewModel
        binding.executePendingBindings()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseDatabase = FirebaseDatabase.getInstance("https://task-206a6-default-rtdb.asia-southeast1.firebasedatabase.app")
       //databaseReference= firebaseDatabase.getReference("banknames")
        binding.linearLayout2.visibility = View.INVISIBLE
        getAllListData()

        binding.fab.setOnClickListener { view ->
           showPaymentDialog(-1)
         }
        PaymentListAdapter.setOnItemSelectedListener(this)
        val swipeHandler = object : SwipeLeftDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position :Int = viewHolder.adapterPosition
                MaterialAlertDialogBuilder(requireActivity(), R.style.RoundShapeTheme)
                    .setTitle("Do you want Delete!")
                    .setPositiveButton("Yes") { dialog, which ->
                        //SharedPrefManager.getInstance(applicationContext).isLogedout()
                        binding.progressBar2.visibility = View.VISIBLE
                        CoroutineScope(IO).launch {
                            UserDataBase.getInstance(requireActivity()).userDao().deleteUser(paymentList[position].id.toString())

                        }

                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        //binding.progressBar2.visibility = View.VISIBLE
                        dialog.dismiss()
                        // DisplayAllUsers()
                    }
                    .show()
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                   binding.progressBar2.visibility = View.GONE
                    getAllListData()

                    // binding.progressBar.visibility = View.GONE
                },2000)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    // dialog show on inset data OR Edit data
    private fun showPaymentDialog(position: Int) {
        val userDialog = BottomSheetDialog(requireContext())
        bindingUserData = DialogUserdataBinding.inflate(layoutInflater)
        userDialog.setContentView(bindingUserData.root)

        // get the current date and format
        val c: Calendar = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c.time)
        bindingUserData.editTextDate.setText(formattedDate)


        viewModel.getBankNames(requireContext())
        viewModel.list.observe(this){
            val adapter = BankSpinnerAdapter(requireContext(),it)
            bindingUserData.spinnerMonth.adapter = adapter
            adapter.notifyDataSetChanged()
            for (x in it.indices ){
                banknameList.add(it[x].name)
            }
            Log.e("SIZE","${banknameList.size}")
            if (position != -1) {
                for (x in banknameList.indices) {
                    if (paymentList[position].bankname == banknameList[x].toString()) {
                        bindingUserData.spinnerMonth.setSelection(x)
                        break
                    }
                }
            }
        }


        if (position != -1){
            bindingUserData.savePayment.setText("Update")
            bindingUserData.editTextCredit.setText(paymentList[position].creditAmount.toString())
            bindingUserData.editTextDeposit.setText(paymentList[position].depositAmount.toString())
            bindingUserData.editTextDate.setText(paymentList[position].date)

        }


        bindingUserData.spinnerMonth.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
               // bankName = parent?.getItemAtPosition(position).toString()
                bankName =banknameList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        bindingUserData.editTextDate.setOnClickListener {
            showDateDialog()
        }

        bindingUserData.savePayment.setOnClickListener {
            if (bindingUserData.editTextCredit.text.toString().trim().isNotEmpty() && bindingUserData.editTextCredit.text.toString().isNotEmpty() && bindingUserData.editTextDate.text.toString().trim().isNotEmpty()){
                if (position != -1){
                    CoroutineScope(IO).launch {
                        UserDataBase.getInstance(requireContext()).userDao().updateUserData(
                            UserData(
                                paymentList[position].id,bindingUserData.editTextCredit.text.toString().trim().toDouble(),
                                bindingUserData.editTextDeposit.text.toString().trim().toDouble(),
                                bankName,
                                bindingUserData.editTextDate.text.toString().trim())
                        )
                    }
                }else{
                    CoroutineScope(IO).launch {
                        UserDataBase.getInstance(requireContext()).userDao().insertUserData(
                            UserData(
                                0,bindingUserData.editTextCredit.text.toString().trim().toDouble(),
                                bindingUserData.editTextDeposit.text.toString().trim().toDouble(),
                                bankName,
                                bindingUserData.editTextDate.text.toString().trim() )
                        )
                    }
                }

                userDialog.dismiss()
                getAllListData()
            }else{
                Toast.makeText(requireContext(),"Fields are not empty",Toast.LENGTH_LONG).show()
            }

        }


        userDialog.show()
    }

    // dialog show on date selection
    private fun showDateDialog() {
        val datePickerDialog = DatePickerDialog( // on below line we are passing context.
            requireContext(),
            { view, year, monthOfYear, dayOfMonth -> // on below line we are setting date to our edit text.
                // binding.editTextJoinDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                var cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd-MM-yyyy")
                date = sdf.format(cal.time)
                bindingUserData.editTextDate.setText(date)
                // Log.e("picker","$date")
            },  // on below line we are passing year,
            // month and day for selected date in our date picker.
            year, month, day
        )
        // at last we are calling show to
        // display our date picker dialog.
        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show()

    }

    private fun getAllListData() {
       viewModel.getAllUsersData(requireContext())

        viewModel.userlist.observe(this){
            binding.progressBar2.visibility = View.GONE
           if (it.size !=0) {
               viewModel.setAdapter(it)
               paymentList = it as ArrayList<UserData>
               binding.linearLayout2.visibility = View.VISIBLE
           }
        }

        viewModel.getTotal(requireContext())
        viewModel.list2.observe(this){
            Log.e("TOTAL","Total: ${it[0]}")
            if (it.isNotEmpty()) {
                binding.textView6.text = it[0].toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(userData: UserData, position: Int) {
        showPaymentDialog(position)
    }
}