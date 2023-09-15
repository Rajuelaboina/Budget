package com.task.task.expndlistview

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.task.task.R

class CaAdapter internal constructor(
    private val context: Context,
    private val titleList: ArrayList<String>,
    private val dataList:HashMap<String,List<String>>):BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return dataList[titleList[groupPosition]]!![childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return  dataList[titleList[groupPosition]]!!.size
    }

     override fun getGroup(groupPosition: Int): Any {
        return titleList[groupPosition]
    }

   override fun getGroupId(groupPosition: Int): Long {
       return groupPosition.toLong()
    }


    override fun getGroupCount(): Int {
       return titleList.size
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val st = getGroup(groupPosition) as String
        if (convertView==null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item,null)
        }

        val tv = convertView!!.findViewById<TextView>(R.id.listView)
        tv.setTypeface(null,Typeface.BOLD)

        tv.text = st

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
         var convertView = convertView
        val st = getChild(groupPosition,childPosition) as String
        if (convertView==null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item,null)
        }

        val tv = convertView!!.findViewById<TextView>(R.id.listView)
        tv.text = st

        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }


}