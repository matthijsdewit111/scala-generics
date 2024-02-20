package org.example.secret.variance2

import scala.reflect.{ClassTag, classTag}

case class Box[A](content: A)

abstract class Machine[-T : ClassTag, U] {
  def getRawMaterial[A <: T : ClassTag]: Box[A]
  def makeProduct[A](input: Box[A]): Box[U]
  def getMaterialAndMakeProduct: Box[U] = makeProduct(getRawMaterial)
}

class Wood
class OakWood extends Wood
case class Chair(material: String)

object WoodenChairMachine extends Machine[Wood, Chair] {

  override def getRawMaterial[A <: Wood](implicit classTag: ClassTag[A]): Box[A] = {
    println(classTag)
    Box(classTag.runtimeClass.newInstance().asInstanceOf[A])
  }

  override def makeProduct[A](input: Box[A]): Box[Chair] = Box(Chair("wood"))
}

object ChairFactory {
  private val machine: Machine[OakWood, Chair] = WoodenChairMachine
  implicit val ct: ClassTag[OakWood] = ClassTag.apply((new Wood).getClass)

  def makeChair(): Box[Chair] = {
    machine.makeProduct(machine.getRawMaterial)
//     machine.getMaterialAndMakeProduct
  }
}

object MissingClassVariance2 {
  def main(args: Array[String]): Unit = {
    val chairBox = ChairFactory.makeChair()
    println(chairBox.content.material)
  }
}
