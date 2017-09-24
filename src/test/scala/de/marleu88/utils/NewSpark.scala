package de.marleu88.utils

import org.apache.spark.sql.SparkSession
import org.specs2.mutable.After

trait NewSpark extends After {

  val appName = "myApp"

  implicit val spark = SparkSession.builder()
    //    .config("spark.local.‌​dir", "/data/home/MLeuthold/tmp")
    //    .config("spark.debug.maxToStringFields", 9999)
    .master("local")
    .appName(appName)
    .getOrCreate()

  override def after {
    spark.stop()
  }
}