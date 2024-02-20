package org.example.part3

import scala.reflect.ClassTag
import scala.reflect.runtime.universe.TypeTag

object Example2 {

  object Extractor {
    def extract[T: ClassTag](list: List[Any]): List[T] = list.flatMap {
      case element: T => Some(element)
      case _ => None
    }
  }

  def main(args: Array[String]): Unit = {
    val list   = List(List(1, 2), List("a", "b"))
    val result = Extractor.extract[Seq[Int]](list)
    println(result)
  }
}
