package com.task.jetpackcompose

class Program {
    fun addTwoNumbers(a:Int,b:Int){  //normal fun
        val sum = a+b
        println(sum)
    }

    fun addTwoNumbers(a:Int,b:Int,action:MyInterface){ //using Interface
        val sum = a+b
        action.execute(sum)
       // println(sum)
    }
    fun addTwoNumbers(a:Int,b:Int,order:(Int,Int)-> Unit){    //higher order fun

        order(a,b)
        //println(sum)
    }

    fun evenNumber(a:Int,evenOrder:(Int)->Unit){

        evenOrder(a)
    }

    fun subTwoNum(x:Int,y:Int,sub:(Int,Int)->Int){

         //sub(x,y)
        val re = sub(x,y)
        println("inside higher order $re")
    }

    fun mulTwoNum(x: Int, y: Int, orderMul: (Int, Int) -> Unit){
        orderMul(x,y)
    }
}
fun main(args:Array<String>){
   val program =Program()



    program.addTwoNumbers(2,3)
    program.addTwoNumbers(2,3,object :MyInterface{
        override fun execute(sum: Int) {
            println(sum)
        }

    })

    val lambda:(Int)->Unit ={s:Int-> println(s) }  //lambda expression
   // program.addTwoNumbers(2,3,lambda)
    program.addTwoNumbers(2,3,{a:Int,b:Int-> println("addTwonum = ${a+b}") })

    val bb:(Int)->Unit ={a-> println("${a%2==0}  $a is even number")}
    program.evenNumber(4,bb)

    val s :(Int,Int)->Int = { x,y-> x-y }
    //program.subTwoNum(10,5,s)                  // first way
    //program.subTwoNum(10,5,{ x,y-> x-y })   // second way

    program.subTwoNum(10,5){ x,y-> x-y }  // third way

    // closures
    var result =0
    val mul :(Int,Int)-> Unit = {x,y-> result = x*y }
    program.mulTwoNum(2,3,mul)
    println("2*3 = $result")
}
interface MyInterface{
    fun execute(sum:Int)
}