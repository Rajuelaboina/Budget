package com.task.task.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.task.listeners.OnItemListener
import com.task.task.R
import com.task.task.databinding.PaymentlistItemBinding
import com.task.task.model.UserData

class PaymentListAdapter : RecyclerView.Adapter<PaymentListAdapter.MyViewHolder>() {
    private var list = ArrayList<UserData>()
    lateinit var context: Context

    fun updateList(userList: List<UserData>) {
        this.list = userList as ArrayList<UserData>
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context =parent.context
        return MyViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.paymentlist_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            myListener.onItemClicked(list[position],position)
        }
        val t = list[position].availableAmount
        if (t > 0){
            holder.binding.textViewAvailableBalance.setTextColor(ContextCompat.getColor(holder.itemView.context,
                R.color.green
            ))
        }else if (t<0){
            holder.binding.textViewAvailableBalance.setTextColor(ContextCompat.getColor(holder.itemView.context,
                R.color.red
            ))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(var binding: PaymentlistItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(userData: UserData) {
            binding.model = userData
        }
    }
    companion object{
        lateinit var myListener: OnItemListener
        fun setOnItemSelectedListener(listener : OnItemListener){
            myListener =listener
        }

    }
}