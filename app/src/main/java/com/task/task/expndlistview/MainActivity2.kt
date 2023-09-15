package com.task.task.expndlistview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.task.task.R

class MainActivity2 : AppCompatActivity() {
    var listDetails = HashMap<String, List<String>>()
    lateinit var listTitles: ArrayList<String>
    lateinit var expandableListView: ExpandableListView
    lateinit var expandableListAdapter: ExpandableListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        expandableListView = findViewById(R.id.expandableListView)
        listDetails = getListData()
        Log.e("Group", "listDetails:" + listDetails.size)
        listTitles = ArrayList<String>(listDetails.keys)

        Log.e("listTitles", "listTitles:" + listTitles.size)

        //expandableListAdapter = CustomAdapter(applicationContext, listTitles, listDetails)
        expandableListAdapter = CaAdapter(applicationContext, listTitles, listDetails)
        expandableListView.setAdapter(expandableListAdapter)
        expandableListView.setOnGroupExpandListener {
            Log.e("GroupExpand", "GroupExpand:" + it)
        }
        expandableListView.setOnGroupCollapseListener {
            Log.e("GroupCollapse", "GroupCollapse:" + it)
        }
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

            Log.e("groupPosition", "groupPosition:" + groupPosition)
            Log.e("childPosition", "childPosition:" + childPosition)
            Log.e("gg", "gg:" + listTitles[groupPosition])
            Log.e("gg", "gg:" + listDetails[listTitles[groupPosition]]!![childPosition])
            false
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun getListData(): HashMap<String, List<String>> {
        var map = HashMap<String, List<String>>()
        val cricket = ArrayList<String>()
        cricket.add("India")
        cricket.add("Pakistan")
        cricket.add("Australia")
        cricket.add("England")
        cricket.add("South Africa")

        val football = ArrayList<String>()
        football.add("Brazil")
        football.add("Spain")
        football.add("Germany")
        football.add("Netherlands")
        football.add("Italy")

        val basketball = ArrayList<String>()
        basketball.add("United States")
        basketball.add("Spain")
        basketball.add("Argentina")
        basketball.add("France")
        basketball.add("Russia")

        map.put("Cricket Team", cricket)

        map.put("FootBall Team", football)

        map.put("Basketball Team", basketball)
        return map


    }
}