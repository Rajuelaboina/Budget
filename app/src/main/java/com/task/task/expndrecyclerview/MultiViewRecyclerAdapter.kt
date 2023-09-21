package com.phycae.recyclerviewitemex

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.task.Constants
import com.task.task.R

class MyRecyclerAdapter(private val context: Context,private val list:  MutableList<ParentData2>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectionPosition = -1


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.PARENT){
            val roowView = LayoutInflater.from(parent.context).inflate(R.layout.parent_row,parent,false)
                GroupViewHolder(roowView)
        } else {
            val roowView = LayoutInflater.from(parent.context).inflate(R.layout.child_row,parent,false)
            ChildViewHolder(roowView)
        }
    }


    override fun getItemCount(): Int {
       return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataList = list[position]
        if (dataList.type == Constants.PARENT){
            holder as GroupViewHolder
            holder.apply {
              parentTV.text = dataList.parentTitle
              downIV.setOnClickListener {
                  expandOrCollapseParentItem(dataList,position,downIV)
                  selectionPosition = position
              }
            }
            holder.itemView.setOnClickListener {
                Log.e("POSITION","position: ${dataList.parentTitle}")
            }
        }else{
            holder as ChildViewHolder
            holder.apply {
                val singleServices = dataList.subList.first()
                childTv.text = singleServices.TypeOfActivity

                holder.itemView.setOnClickListener {
                    selectionPosition = position
                    Log.e("POSITION2","position2: ${singleServices.Message}")
                     notifyDataSetChanged()

                }

            }
            if (selectionPosition == position){
                holder.itemView.setBackgroundColor(R.color.black)

            }else {
                holder.itemView.setBackgroundColor(R.color.white)
            }

        }

    }

    private fun expandOrCollapseParentItem(
        singleBoarding: ParentData2,
        position: Int,
        downIV: ImageView
    ) {
         if (singleBoarding.isExpanded){
             collapseParentRow(position)
             downIV.setImageResource(R.drawable.ic_dropdown)
         }else{
             expandParentRow(position)
             downIV.setImageResource(R.drawable.ic_drop_up_24)
         }

    }
    private fun collapseParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        list[position].isExpanded = false
        if(list[position].type==Constants.PARENT){
            services.forEach { _ ->
                list.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }

    }

    private fun expandParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        currentBoardingRow.isExpanded = true
        var nextPosition = position
        if(currentBoardingRow.type==Constants.PARENT){

            services.forEach { service ->
                val parentModel =  ParentData2()
                parentModel.type = Constants.CHILD
                val subList : ArrayList<GroupActivitiesItem> = ArrayList()
                subList.add(service)
                parentModel.subList=subList
                list.add(++nextPosition,parentModel)
            }
            notifyDataSetChanged()
        }

    }



    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemId(position: Int): Long {
        return return position.toLong()
    }
}
class GroupViewHolder(roowView: View): RecyclerView.ViewHolder(roowView) {
    val parentTV = roowView.findViewById<TextView>(R.id.parent_Title)
    val downIV = roowView.findViewById<ImageView>(R.id.down_iv)
}

class ChildViewHolder(roowView: View) : RecyclerView.ViewHolder(roowView) {
     val childTv = roowView.findViewById<TextView>(R.id.child_Title)
}
