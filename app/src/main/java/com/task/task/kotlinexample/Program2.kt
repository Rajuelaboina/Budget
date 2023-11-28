package com.task.task.kotlinexample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Program2 {
    fun reverseStringDisplay(s:String,myFunction:(String)-> String){
         val result = myFunction(s)
        println(result)
    }
    fun evenNum(x:Int, enven: (Int) -> Unit) {
        return enven(x)
    }
}

fun main(args:Array<String>){
    val program = Program2()
    //program.reverseStringDisplay("Hello",{s->s.reversed()})  // OR
   // program.reverseStringDisplay("Hello"){it.reversed()}  // it =s-> s.reversed()  and it means a single parameter

  //  program.evenNum(5){ if (it%2==0)print("even") else println("odd")    }



}
suspend fun doWorld() = coroutineScope {
    repeat(100){
        println("my job")
        delay(500)
    }
    launch {
        delay(1000L)
        println("coroutine")
    }
    println("hello main")
}