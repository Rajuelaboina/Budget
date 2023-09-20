package com.task.task

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.task.model.BankNames
import org.json.JSONArray
import org.json.JSONObject


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

       val jsonString = getJsonString(applicationContext,"data.json")
        val gson = Gson()
        val stringType = object :TypeToken<List<BankNames>>(){}.type
        val list:List<BankNames> = gson.fromJson(jsonString,stringType)
        /*Log.e("GSON DATA","list: ${list.size}")
        list.forEach {
            Log.e("GSON DATA","list: ${it.name}")
        }*/
        /*try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jo: JSONObject = jsonArray.getJSONObject(i)
                Log.e("Volley  DATA","list:"+jo.has("name"))
                Log.e("Volley  DATA","list2:"+jo.has("Name"))

                val id = jo.optString("Name",null)
                Log.e("Volley  DATA","list: $id")
                // do stuff

            }
        }catch (e:Exception){

        }*/


    }

    private fun getJsonString(context: Context, fileName: String) :String?{
        var string = ""

        try {
            string = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }catch ( e: Exception){
            e.printStackTrace()
            return null
        }
        return string
    }

    private fun getData(){
        val parentData: Array<String> = arrayOf("Andhra Pradesh", "Telangana", "Karnataka", "TamilNadu")
        val childDataData1: MutableList<ChildData> = mutableListOf(ChildData("Anathapur"),ChildData("Chittoor"),ChildData("Nellore"),ChildData("Guntur"))
        val childDataData2: MutableList<ChildData> = mutableListOf(ChildData("Warangal"), ChildData("Karimnagar"), ChildData("Jangon"))
        val childDataData3: MutableList<ChildData> = mutableListOf(ChildData("Chennai"), ChildData("Erode"))

        val parentObj1 = ParentData(parentTitle = parentData[0], subList = childDataData1)
        val parentObj2 = ParentData(parentTitle = parentData[1], subList = childDataData2)
        val parentObj3 = ParentData(parentTitle = parentData[2])
        val parentObj4 = ParentData(parentTitle = parentData[3], subList = childDataData3)

        listData.add(parentObj1)
        listData.add(parentObj2)
        listData.add(parentObj3)
        listData.add(parentObj4)
    }
}