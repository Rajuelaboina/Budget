package com.task.task.kotlinexample

class Person(name: String, age: Int) {
    var name:String =name
    var age:Int = age

    fun display(){
        println("this is person class")
    }
}
fun main(args:Array<String>){
    val person = Person("Java", 50)
    /*person.name = "Raju"
    person.age = 21*/
    with(person){
        person.name = "Android"
        person.age = 21
    }
    person.apply {
        person.name = "Kotlin"
        person.age = 10
    }.display()

    println(person.name)
    println(person.age)
}