package org.example.part2

import scala.reflect.runtime.universe.{TypeTag, typeOf}

object Example1 {
  private def processBox[T, U](box2: Box2[T]): Unit = {
    box2.item1 match {
      case item: U => println("Item 1 has the requested type")
      case _ => println("Item 1 does not have the requested type")
    }
    box2.item2 match {
      case item: U => println("Item 2 has the requested type")
      case _ => println("Item 2 does not have the requested type")
    }
  }

  def main(args: Array[String]): Unit = {
    val intStringBox2 = Box2(5, "hello")

    processBox[Any, Int](intStringBox2)
  }
}
