package org.example.part4

import org.apache.spark.SparkConf
import org.apache.spark.sql.{LowPrioritySQLImplicits, SparkSession}

trait Spark extends LowPrioritySQLImplicits {
  @transient lazy val spark: SparkSession = SparkSession.builder.getOrCreate

  def initializeSpark(appName: String): Unit = {
    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster("local[*]")

    SparkSession.builder.config(conf).getOrCreate
  }
}
