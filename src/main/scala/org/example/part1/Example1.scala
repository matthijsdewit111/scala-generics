package org.example.part1

object Example1 {
  private def processBox(box: Box[_]): Unit =
    box match {
      case Box(content: Int)    => println("Content is Int")
      case Box(content: String) => println("Content is String")
      case _ => println("Content is something else")
    }

  def main(args: Array[String]): Unit = {
    val intBox    = Box(5)
    val stringBox = Box("Hello")

    processBox(intBox)
    processBox(stringBox)
  }
}
