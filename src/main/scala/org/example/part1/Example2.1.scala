package org.example.part1

sealed trait BoxValue
case class IntBoxValue(content:       Int) extends BoxValue
case class StringBoxValue(content:    String) extends BoxValue
case class SeqIntBoxValue(content:    Seq[Int]) extends BoxValue
case class SeqStringBoxValue(content: Seq[String]) extends BoxValue

object Example21 {
  private def processBox[T <: BoxValue](box: Box[T]): Unit =
    box match {
      case Box(IntBoxValue(content:       Int))         => println("Content is Int")
      case Box(StringBoxValue(content:    String))      => println("Content is String")
      case Box(SeqIntBoxValue(content:    Seq[Int]))    => println("Content is Seq[Int]")
      case Box(SeqStringBoxValue(content: Seq[String])) => println("Content is Seq[String]")
      case _ => println("Content is something else")
    }

  def main(args: Array[String]): Unit = {
    val intBox       = Box(IntBoxValue(5))
    val stringBox    = Box(StringBoxValue("Hello"))
    val seqIntBox    = Box(SeqIntBoxValue(Seq(1, 2, 3)))
    val seqStringBox = Box(SeqStringBoxValue(Seq("a", "b", "c")))

    processBox(intBox)
    processBox(stringBox)
    processBox(seqIntBox)
    processBox(seqStringBox)
  }
}
