package org.example.secret

case class Box[A](content: A)

trait Machine[T, U] {
  def getRawMaterial: Box[T]
  def makeProduct(input: Box[T]): Box[U]
  def getMaterialAndMakeProduct: Box[U] = makeProduct(getRawMaterial)
}

case class Metal()
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

object MissingClass {
  def main(args: Array[String]): Unit = {
    val chairBox = ChairFactory.makeChair()
    println(chairBox.content.material)
    // MultiChairFactory.makeChairs()
  }
}

//object MetalChairMachine extends Machine[Metal, Chair] {
//
//  override def getRawMaterial: Box[Metal] = Box(new Metal)
//
//  override def makeProduct(input: Box[Metal]): Box[Chair] = Box(new Chair)
//}

//object MultiChairFactory {
//  private val machines: List[Machine[_, Chair]] = List(WoodenChairMachine, MetalChairMachine)
//
//  def makeChairs(): Unit = {
//    for (
//      machine <- machines
//    ) yield {
//      machine.makeProduct(machine.getRawMaterial)
//    }
//  }
//}
