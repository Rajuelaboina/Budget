package com.task.task

import java.util.regex.Pattern

open class Employee(val a:Int,val b:Int){

}
class B:Employee(4,5)
fun main(args:Array<String>){

  /*fun Employee.operate():Int{
      return a+b
  }
    fun B.operate2():Int{
        return a*b
    }
    fun display(a:Employee){
       println( a.operate())

    }
    display(B())*/
  val pattren = Regex("^a")
    /*println(pattren.containsMatchIn("abc"))
    println(pattren.containsMatchIn("bcda"))
    val pt = Regex("\\s+")
    val list = pt.split("this is kotlin example")
    list.forEach {
        println(it)
    }*/
    //val emailpt = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
  val emailpt = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    val pttern= Pattern.compile(emailpt)
    val ma = pttern.matcher("_deo.sample@gmail.com")
   // println(ma.matches())

    val mobileString = "[6-9][0-9]{9}"
   val pt = Pattern.compile(mobileString)
  // 1) Begins with 0 or 91
  // 2) Then contains 6 or 7 or 8 or 9.
  // 3) Then contains 9 digits
   /*val p: Pattern = Pattern.compile("(0|91)?[6-9][0-9]{9}")

    val match = p.matcher("06532147896")
    println(match.matches())*/

  // password pattern

     /* ^                 # start-of-string
      (?=.*[0-9])       # a digit must occur at least once
      (?=.*[a-z])       # a lower case letter must occur at least once
      (?=.*[A-Z])       # an upper case letter must occur at least once
      (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
      (?=\\S+$)          # no whitespace allowed in the entire string
      .{4,}             # anything, at least six places though
      $                 # end-of-string*/

  val ptrn ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
  val p = Pattern.compile(ptrn)

  val match = p.matcher("1rajU+9591")
  println(match.matches())
}
