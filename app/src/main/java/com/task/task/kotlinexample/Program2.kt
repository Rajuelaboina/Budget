package com.task.task.kotlinexample

class Program2 {
    fun reverseStringDisplay(s:String,myFunction:(String)-> String){
         val result = myFunction(s)
        println(result)
    }
}
fun main(args:Array<String>){
    val program = Program2()
    //program.reverseStringDisplay("Hello",{s->s.reversed()})  // OR
    program.reverseStringDisplay("Hello"){it.reversed()}  // it =s-> s.reversed()  and it means a single parameter

}