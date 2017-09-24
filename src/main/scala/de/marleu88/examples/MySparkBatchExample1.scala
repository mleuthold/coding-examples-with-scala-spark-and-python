package de.marleu88.examples

import com.beust.jcommander.{JCommander, Parameter}
import com.typesafe.scalalogging.LazyLogging
import org.apache.avro.generic.GenericData.StringType
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.lit

object MySparkBatchExample1 extends App with LazyLogging {

  @Parameter(names = Array("--input-path"), required = true, description = "Path to input data")
  var INPUT_PATH = ""
  @Parameter(names = Array("--output-path"), required = true, description = "Path to output data to in JSON and Parquet format")
  var OUTPUT_PATH = ""

  JCommander.newBuilder().addObject(this).build.parse(args: _ *)

  val appName = "myApp"
  implicit val spark = SparkSession.builder()
    //.master("local")
    .appName(appName)
    .getOrCreate()

  run(INPUT_PATH, OUTPUT_PATH)

  def run(inputPath: String, outputPath:String)(implicit spark: SparkSession):DataFrame={
    logger.info(s"run $appName")

    // read data
    val inputDf = readData(inputPath)

    // transform data
    val outputDf = transformData(inputDf)

    // write data
    writeData(outputDf, outputPath)

    outputDf
  }

  def readData(path: String)(implicit spark: SparkSession): DataFrame={
    logger.info(s"read data from $path")
    spark.read.json(path)
  }

  def readData(paths: List[String])(implicit spark: SparkSession): DataFrame={
    logger.info(s"read data from $paths")
    spark.read.json(paths:_*)
  }

  def transformData(df: DataFrame)(implicit spark: SparkSession):DataFrame={
    logger.info("transform data")
    df.withColumn("myColumn",lit("some text"))
  }

  def writeData(df: DataFrame, basePath: String): Unit ={
    logger.info("cache data frame")
    df.cache

    logger.info(s"write data frame as json to $basePath")
    df.write.mode(SaveMode.Overwrite).json(basePath + "/json/")

    logger.info(s"write data frame as parquet to $basePath")
    df.write.mode(SaveMode.Overwrite).parquet(basePath + "/parquet/")
  }

}
