package com.task.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    val listData : MutableList<ParentData> = ArrayList()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        recyclerView = findViewById(R.id.recyclerView2)
         getData()
         val adapter = MyRecyclerAdapter(applicationContext,listData)
        recyclerView.adapter = adapter
    }

    private fun getData(){
        val parentData: Array<String> = arrayOf("Andhra Pradesh", "Telangana", "Karnataka", "TamilNadu")
        val childDataData1: MutableList<ChildData> = mutableListOf(ChildData("Anathapur"),ChildData("Chittoor"),ChildData("Nellore"),ChildData("Guntur"))
        val childDataData2: MutableList<ChildData> = mutableListOf(ChildData("Warangal"), ChildData("Karimnagar"), ChildData("Jangon"))
        val childDataData3: MutableList<ChildData> = mutableListOf(ChildData("Chennai"), ChildData("Erode"))

        val parentObj1 = ParentData(parentTitle = parentData[0], subList = childDataData1)
        val parentObj2 = ParentData(parentTitle = parentData[1], subList = childDataData2)
        val parentObj3 = ParentData(parentTitle = parentData[2])
        val parentObj4 = ParentData(parentTitle = parentData[1], subList = childDataData3)

        listData.add(parentObj1)
        listData.add(parentObj2)
        listData.add(parentObj3)
        listData.add(parentObj4)
    }
}