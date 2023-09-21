package com.task.task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.task.task.adapter.BankSpinnerAdapter
import com.task.task.adapter.PaymentListAdapter
import com.task.task.database.UserDataBase
import com.task.task.databinding.DialogUserdataBinding
import com.task.task.databinding.FragmentPaymentlistBinding
import com.task.task.listeners.OnItemListener
import com.task.task.model.UserData
import com.task.task.utils.DateUtils
import com.task.task.utils.SwipeLeftDeleteCallback
import com.task.task.viewmodel.BankNamesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class PaymentListFragment : Fragment(), OnItemListener {

    private var _binding: FragmentPaymentlistBinding? = null
    private lateinit var viewModel: BankNamesViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase
   /// private lateinit var databaseReference: DatabaseReference
   // private lateinit var firebaseFireStore : FirebaseFirestore
    private lateinit var bindingUserData: DialogUserdataBinding
    private var date = ""
    private var year = 0
    private var month = 0
    private var day = 0
    private var bankName = ""
    private var paymentList = ArrayList<UserData>()
    private var banknameList = mutableListOf<String>()
    private var creditValue = 0.0
    private var withdrawValue  = 0.0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        /*databaseReference=  firebaseDatabase.reference.child("BankExpenses").child("BankNames")
        FirebaseApp.initializeApp(requireContext())
         firebaseFireStore = FirebaseFirestore.getInstance()
         firebaseFireStore.collection("BankNames").get().addOnSuccessListener {
             var banknamesList = mutableListOf<BankNames>()
             for (document in it) {
                // val users = document.toObject(BName::class.java)
                 Log.d("RRRRRRRRRRRRRRRR<>>>>>>>>>>>>", "${document.id} => ${document.data}")

                 val bankname = document.getString("bankname")

                 banknameList.add(bankname.toString())
                 val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,banknameList)
                 bindingUserData.spinnerMonth.adapter = adapter
                 adapter.notifyDataSetChanged()
                *//* val ad = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,banknameList)
                 bindingUserData.autoCompleteTextView.setAdapter(ad)
                 ad.notifyDataSetChanged()*//*
             }
             banknamesList.forEach {
                 Log.d("RRRR List", "${it}")
             }

         }*/

        binding.fab.setOnClickListener {
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
                Handler(Looper.getMainLooper()).postDelayed({
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
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun showPaymentDialog(position: Int) {
        val userDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
        bindingUserData = DialogUserdataBinding.inflate(layoutInflater)
        userDialog.setContentView(bindingUserData.root)
        bindingUserData.textViewRentPaymentDialogTitle .text = DateUtils.setSpannable(requireActivity(),"Payment of Saving",0,7,14)

        // get the current date and format
        val c: Calendar = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c.time)
        bindingUserData.editTextDate.setText(formattedDate)

       /* fire store data
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,banknameList)
        bindingUserData.spinnerMonth.adapter = adapter
        adapter.notifyDataSetChanged()*/
        viewModel.getBankNames(requireContext())
        viewModel.list.observe(this){
            val adapter = BankSpinnerAdapter(requireContext(),it)
            bindingUserData.spinnerMonth.adapter = adapter
            adapter.notifyDataSetChanged()
            for (x in it.indices ){
                banknameList.add(it[x].name)
            }
            //Log.e("SIZE","${banknameList.size}")
            if (position != -1) {
                for (x in banknameList.indices) {
                    if (paymentList[position].bankname == banknameList[x]) {
                        bindingUserData.spinnerMonth.setSelection(x)
                        break
                    }
                }
            }
        }



        if (position != -1){

               /* for (x in banknameList.indices) {
                    if (paymentList[position].bankname == banknameList[x]) {
                        bindingUserData.spinnerMonth.setSelection(x)
                        break
                    }
                } */
            bindingUserData.savePayment.text = "Update"
            bindingUserData.editTextCredit.setText(paymentList[position].creditAmount.toString())
            bindingUserData.editTextwithdraw.setText(paymentList[position].withdrawAmount.toString())
            bindingUserData.editTextAvailable.setText(paymentList[position].availableAmount.toString())
            bindingUserData.editTextDate.setText(paymentList[position].date)
        }
        bindingUserData.editTextwithdraw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             //   Log.e("SSSSS","s:${s.toString().length}")
                if (s.toString().isNotEmpty()) {
                    withdrawValue = s.toString().trim().toDouble()
                    showTotal(bindingUserData.editTextAvailable)
                }else if (s.toString().isEmpty()){
                    withdrawValue = 0.0
                    showTotal(bindingUserData.editTextAvailable)
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        bindingUserData.editTextCredit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    creditValue = s.toString().trim().toDouble()
                    showTotal(bindingUserData.editTextAvailable)
                }else if (s.toString().isEmpty()){
                    withdrawValue = 0.0
                    showTotal(bindingUserData.editTextAvailable)
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })


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
            //if (bindingUserData.editTextCredit.text.toString().trim().isNotEmpty() && bindingUserData.editTextCredit.text.toString().isNotEmpty() && bindingUserData.editTextDate.text.toString().trim().isNotEmpty()){
                if (position != -1){
                    CoroutineScope(IO).launch {
                        UserDataBase.getInstance(requireContext()).userDao().updateUserData(
                            UserData(
                                paymentList[position].id,
                                bankName,
                                bindingUserData.editTextCredit.text.toString().trim().toDouble(),
                                bindingUserData.editTextwithdraw.text.toString().trim().toDouble(),
                                bindingUserData.editTextAvailable.text.toString().trim().toDouble(),
                                bindingUserData.editTextDate.text.toString().trim())
                        )
                    }
                }else{
                    CoroutineScope(IO).launch {
                        UserDataBase.getInstance(requireContext()).userDao().insertUserData(
                            UserData(
                                0,
                                bankName,
                                creditValue,
                                withdrawValue,
                                bindingUserData.editTextAvailable.text.toString().trim().toDouble(),
                                bindingUserData.editTextDate.text.toString().trim() )
                        )

                        //firebaseDatabase = FirebaseDatabase.getInstance("https://task-206a6-default-rtdb.asia-southeast1.firebasedatabase.app")
                        /*databaseReference=  firebaseDatabase.reference.child("BankExpenses")
                        val pushId = databaseReference.push().getKey()
                        databaseReference.child(pushId.toString()).setValue( UserData(
                            0,bindingUserData.editTextCredit.text.toString().trim().toDouble(),
                            bindingUserData.editTextDeposit.text.toString().trim().toDouble(),
                            bankName,
                            bindingUserData.editTextDate.text.toString().trim() )
                        )
*/
                        creditValue = 0.0
                        withdrawValue = 0.0
                    }
                }
                userDialog.dismiss()
                getAllListData()
           /* }else{
                Toast.makeText(requireContext(),"Fields are not empty",Toast.LENGTH_LONG).show()
            }*/
        }
        userDialog.show()
    }

    // show total from credit and withdraw
    private fun showTotal(editTextTotal: TextView) {
         val t = creditValue.minus(withdrawValue)
        editTextTotal.text = t.toString()
        if (t > 0){
            bindingUserData.editTextAvailable.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        }else if (t<0){
          bindingUserData.editTextAvailable.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
        }
    }

    // dialog show on date selection
    @SuppressLint("SimpleDateFormat")
    private fun showDateDialog() {
        val datePickerDialog = DatePickerDialog( // on below line we are passing context.
            requireContext(),
            { view, year, monthOfYear, dayOfMonth -> // on below line we are setting date to our edit text.
                // binding.editTextJoinDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                val cal = Calendar.getInstance()
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
        viewModel.getTotal(requireContext())
        /*viewModel.getBankNames(requireContext())
        viewModel.listBr.observe(this){
            binding.progressBar2.visibility = View.GONE
            banknameList = it as MutableList<String>
            binding.linearLayout2.visibility = View.VISIBLE


        }*/
         
        viewModel.userlist.observe(this){
             binding.progressBar2.visibility = View.GONE
            if (it.size !=0) {
                viewModel.setAdapter(it)
                paymentList = it as ArrayList<UserData>
                binding.linearLayout2.visibility = View.VISIBLE
            }
        }


        viewModel.list2.observe(this){
            //Log.e("TOTAL","Total: ${it[0]}")
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