package com.phycae.recyclerviewitemex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.task.task.R
import com.task.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MovieViewModel
    lateinit var binding: ActivityMainBinding
    val listData : MutableList<ParentData2> = ArrayList()
    val list = ArrayList<String>()
    val child1 = mutableListOf<GroupActivitiesItem>()
    val child2 = mutableListOf<GroupActivitiesItem>()
    val child3 = mutableListOf<GroupActivitiesItem>()
    val child4 = mutableListOf<GroupActivitiesItem>()
    val child5 = mutableListOf<GroupActivitiesItem>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding = DataBindingUtil. setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        binding.viewModel = viewModel
        //binding.setVariable(BR.viewModel,viewModel)
        binding.executePendingBindings()
        viewModel.getAllMovies()
        viewModel.getMoviesObserver().observe(this, Observer {
           // Log.e("MainAcivity","response: ${it.size}")
           /// viewModel.setAdapter(it)
            it.forEach {
                if (!list.contains(it.GroupName)) {
                    if (it.GroupName.equals("")){
                        list.add("OFF WORK")
                    }else {
                        list.add(it.GroupName)
                    }
                }

            }

            it.forEach {
                for (x in list.indices){
                    if ("OFF WORK".equals(it.TypeOfActivity)){
                        child1.add(it)
                          break
                    }else if (list[1].equals(it.GroupName)){
                        child2.add(it)
                        break
                    }
                    else if (list[2].equals(it.GroupName)){
                        child3.add(it)
                        break
                    }
                    else if (list[3].equals(it.GroupName)){
                        child4.add(it)
                        break
                    }
                    else if (list[4].equals(it.GroupName)){
                        child5.add(it)
                        break
                    }
                }
            }
            val parentObj1 = ParentData2(parentTitle = list[0], subList = child1)
            val parentObj2 = ParentData2(parentTitle = list[1], subList = child2)
            val parentObj3 = ParentData2(parentTitle = list[2], subList = child3)
            val parentObj4 = ParentData2(parentTitle = list[3], subList = child4)
            val parentObj5 = ParentData2(parentTitle = list[4], subList = child5)

            listData.add(parentObj1)
            listData.add(parentObj2)
            listData.add(parentObj3)
            listData.add(parentObj4)
            listData.add(parentObj5)
            Log.e("listData","listData: ${listData}")
            val adapter = MyRecyclerAdapter(applicationContext,listData)
            binding.recyclerView.adapter = adapter
        })*/

      //  val snapHelper = LinearSnapHelper()
      //  snapHelper.attachToRecyclerView(binding.recyclerView)
    }

}