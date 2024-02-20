package org.example.part1

import scala.reflect.runtime.universe.{TypeTag, typeOf}

object Example4 {
  private def processBox[T: TypeTag](box: Box[T]): Unit = {
    val t = typeOf[T]
    println(t)
    box match {
      case Box(content: Int)                                      => println("Content is Int")
      case Box(content: String)                                   => println("Content is String")
      case Box(content: Seq[Int]) if t =:= typeOf[Seq[Int]]       => println("Content is Seq[Int]")
      case Box(content: Seq[String]) if t =:= typeOf[Seq[String]] => println("Content is Seq[String]")
      case _ => println("Content is something else")
    }
  }

  def main(args: Array[String]): Unit = {
    val intBox       = Box(5)
    val stringBox    = Box("Hello")
    val seqIntBox    = Box(Seq(1, 2, 3))
    val seqStringBox = Box(Seq("a", "b", "c"))

    processBox(intBox)
    processBox(stringBox)
    processBox(seqIntBox)
    processBox(seqStringBox)
  }
}
