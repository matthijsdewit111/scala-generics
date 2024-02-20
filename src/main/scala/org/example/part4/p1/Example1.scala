package org.example.part4.p1

import org.apache.spark.sql.Dataset
import org.example.part4.Spark

trait UnifiedSchema extends Product
trait SubVersionSchema extends Product

trait EventReader[U <: UnifiedSchema] {
  val eventName:              String
  val eventSubVersionParsers: List[Parser[_ <: SubVersionSchema, U]]

  def readAllEventsAsOneDataSet(): Dataset[U] =
    readEvents().reduce(_ union _)

  private def readEvents(): List[Dataset[U]] =
    for {
      eventSubversionParser <- eventSubVersionParsers
    } yield {
      eventSubversionParser.readAsUnifiedSchema()
    }
}

trait Parser[T <: SubVersionSchema, U <: UnifiedSchema] {
  def readAsSubVersion(): Dataset[T]

  def transform(dataset: Dataset[T]): Dataset[U]

  def readAsUnifiedSchema(): Dataset[U] =
    transform(readAsSubVersion())
}

case class TestUnifiedSchema(name: String, color: Int) extends UnifiedSchema

case class V1Schema(colorName: String, color:       String) extends SubVersionSchema
case class V2Schema(name:      String, colorNumber: Int) extends SubVersionSchema

object V1Parser extends Parser[V1Schema, TestUnifiedSchema] with Spark {

  override def readAsSubVersion(): Dataset[V1Schema] =
    spark
      .read
      .schema(newProductEncoder[V1Schema].schema)
      .json("src/main/resources/v1Events.json")
      .as[V1Schema]

  override def transform(dataset: Dataset[V1Schema]): Dataset[TestUnifiedSchema] =
    dataset
      .map(
        row =>
          TestUnifiedSchema(
            name  = row.colorName,
            color = Integer.parseInt(row.color, 16)
          )
      )
      .as[TestUnifiedSchema]
}

object V2Parser extends Parser[V2Schema, TestUnifiedSchema] with Spark {

  override def readAsSubVersion(): Dataset[V2Schema] =
    spark.createDataset(
      spark
        .sparkContext
        .textFile("src/main/resources/v2Events.csv")
        .zipWithIndex()
        .filter { case (_, index) => index >= 1 } // filter header
        .map(_._1)
        .map(row => {
          val parts = row.split(';')
          V2Schema(parts(0), Integer.parseInt(parts(1)))
        })
    )

  override def transform(dataset: Dataset[V2Schema]): Dataset[TestUnifiedSchema] =
    dataset
      .map(
        row =>
          TestUnifiedSchema(
            name  = row.name,
            color = row.colorNumber
          )
      )
      .as[TestUnifiedSchema]
}

object TestEventReader extends EventReader[TestUnifiedSchema] {
  override val eventName: String = "test"
  override val eventSubVersionParsers: List[Parser[_ <: SubVersionSchema, TestUnifiedSchema]] =
    List(V1Parser, V2Parser)
}

object Example1 extends Spark {
  def main(args: Array[String]): Unit = {
    initializeSpark("test")

    val dataset = TestEventReader.readAllEventsAsOneDataSet()
    dataset.show()
    spark.stop
  }
}
