package org.example.secret.variance

case class Box[+A](content: A)

trait Machine[+T, U] {
  def getRawMaterial: Box[T]
  def makeProduct[A >: T](input: Box[A]): Box[U]
  def getMaterialAndMakeProduct: Box[U] = makeProduct(getRawMaterial)
}

case class Wood()
case class Chair(material: String)

object WoodenChairMachine extends Machine[Wood, Chair] {
  override def getRawMaterial: Box[Wood] = Box(new Wood)
  override def makeProduct[A >: Wood](input: Box[A]): Box[Chair] = Box(Chair("wood"))
}

object ChairFactory {
  private val machine: Machine[_, Chair] = WoodenChairMachine

  def makeChair(): Box[Chair] = {
    machine.makeProduct(machine.getRawMaterial)
//     machine.getMaterialAndMakeProduct
  }
}

object MissingClassVariance extends App {
    val chairBox = ChairFactory.makeChair()
    println(chairBox.content.material)

}
