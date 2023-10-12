package com.task.task.kotlinexample

class Collections {
}
fun main(args:Array<String>){
    /*val list = listOf<Int>(10,20,30,40)

    for (item in list){
        println(item)
    }
    for ( x in 0..list.size-1){
        println(list[x])
    }*/
    /*var myMap = mapOf<Int,String>(1 to "android",2 to "Kotlin" , 3 to "android")
    for (element in myMap){
        println(element)
    }*/

    /*val myNumbers:List<Int> = listOf(2,3,4,5,23,90)
     val smallNum = myNumbers.filter { it < 10 }   // it = num-> num < 10
    println(smallNum)*/

   // val mysqureNum = myNumbers.map { it*it }   // it = num*num  output all
    // 4
    //9
    //16
    //25
    //529
    //8100
    //val mysqureNum = myNumbers.map { it*it }.filter { it<10}
    /*val mysqureNum = myNumbers.filter { it<10}.map { it*it }

    for (item in mysqureNum){
        println(item)
    }*/

    /*val people = listOf<Person>(Person("Android",20), Person("Kotlin",10), Person("Java",50))
    var names = people.filter { it.name.startsWith('j') }.map {it.name}
    for (name in names){
        println(name)
    }*/
    val myNumbers:List<Int> = listOf(2,3,4,5,23,23,90)
    val check1 = myNumbers.all ({it>10})
    println(check1)
    val check2 = myNumbers.any({it>10})
    println(check2)
    val count = myNumbers.count({it>10})
    println(count)
    val duplicate = myNumbers.distinct()
    println(duplicate)
    println( myNumbers.toMutableSet())
   println(myNumbers.find{it>10})

    val mypredicate = {num:Int ->num >10}
    val check5 = myNumbers.all (mypredicate)
}