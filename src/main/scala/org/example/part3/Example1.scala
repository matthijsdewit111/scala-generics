package org.example.part3

import scala.reflect.ClassTag

object Example1 {

  object Extractor {
    def extract[T:ClassTag](list: List[Any]): List[T] = list.flatMap {
      case element: T => Some(element)
      case _ => None
    }
  }

  def main(args: Array[String]): Unit = {
    val list   = List[Any](1, "string1", List(), "string2")
    val result = Extractor.extract[String](list)
    println(result)
  }
}
