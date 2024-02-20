package org.example.part2

import scala.reflect.runtime.universe.{Type, TypeTag, typeOf}

object Example11 {
  private def processBox[T: TypeTag, U: TypeTag](box2: Box2[T]): Unit = {
    println(s"typeOf[T]: ${typeOf[T]}")
    println(s"typeOf[U]: ${typeOf[U]}")
    box2.item1 match {
      case item: U => println(s"Item 1 has the requested type (item is U = ${item.isInstanceOf[U]}, item is Int = ${item.isInstanceOf[Int]}, class = ${item.getClass})")
      case item => println(s"Item 1 does not have the requested type (item is U = ${item.isInstanceOf[U]}, item is Int = ${item.isInstanceOf[Int]}, class = ${item.getClass}))")
    }
    box2.item2 match {
      case item: U => println(s"Item 2 has the requested type (item is U = ${item.isInstanceOf[U]}, item is Int = ${item.isInstanceOf[Int]}, class = ${item.getClass}))")
      case item => println(s"Item 2 does not have the requested type (item is U = ${item.isInstanceOf[U]}, item is Int = ${item.isInstanceOf[Int]}, class = ${item.getClass}))")
    }
  }


  def main(args: Array[String]): Unit = {
    val intStringBox2 = Box2(5, "hello")

    processBox[Any, Int](intStringBox2)
  }
}
