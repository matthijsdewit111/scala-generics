package org.example.secret.simple

case class Box[A](content: A)

abstract class Machine[T, U] {
  def getRawMaterial: Box[T]
  def makeProduct(input: Box[T]): Box[U]
  def getMaterialAndMakeProduct: Box[U] = makeProduct(getRawMaterial)
}

case class Wood()
case class Chair(material: String)

object WoodenChairMachine extends Machine[Wood, Chair] {

  override def getRawMaterial: Box[Wood] = Box(new Wood)

  override def makeProduct(input: Box[Wood]): Box[Chair] = Box(Chair("wood"))
}

object ChairFactory {
  private val machine: Machine[_, Chair] = WoodenChairMachine

  def makeChair(): Box[Chair] = {
//    machine.makeProduct(machine.getRawMaterial)
     machine.getMaterialAndMakeProduct
  }
}

object MissingClassSimple {
  def main(args: Array[String]): Unit = {
    val chairBox = ChairFactory.makeChair()
    println(chairBox.content.material)
  }
}
