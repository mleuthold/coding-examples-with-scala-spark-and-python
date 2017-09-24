package de.marleu88.examples

import com.typesafe.scalalogging.LazyLogging
import de.marleu88.utils.NewSpark
import org.apache.spark.sql.DataFrame
import org.scalatest.{FlatSpec, MustMatchers}

class MySparkBatchExample1Spec extends FlatSpec with MustMatchers with LazyLogging {

  val IS_PRINTING_ENABLED = false
  val IS_SHORT_OUTPUT_ENABLED = true

  "reading a data frame" should "return two data records" in new NewSpark{
    import spark.sqlContext.implicits._

    val df: DataFrame = spark.sparkContext
      .parallelize(List(
        ("A", 1, false),
        ("B", 2, true)
      ))
      .toDF("name", "number", "isGreat")

    if (IS_PRINTING_ENABLED) df.printSchema()
    if (IS_PRINTING_ENABLED) df.show(IS_SHORT_OUTPUT_ENABLED)

    df.count() mustEqual 2
  }

  "readData" should "read JSON files correctly" in new NewSpark {
    val df: DataFrame = MySparkBatchExample1.readData(getClass.getResource("/MySparkBatchExample1Spec/input/").getPath)

    if (IS_PRINTING_ENABLED) df.printSchema()
    if (IS_PRINTING_ENABLED) df.show(IS_SHORT_OUTPUT_ENABLED)

    df.count() mustEqual 2
  }

  "readData" should "read JSON files correctly from multiple paths" in new NewSpark {
    val df: DataFrame = MySparkBatchExample1.readData( List(
      getClass.getResource("/MySparkBatchExample1Spec/input/").getPath ,
        getClass.getResource("/MySparkBatchExample1Spec/secondInput/").getPath)
    )

    if (IS_PRINTING_ENABLED) df.printSchema()
    if (IS_PRINTING_ENABLED) df.show(IS_SHORT_OUTPUT_ENABLED)

    df.count() mustEqual 4
  }

  "transformData" should "return a data frame with a new column" in new NewSpark{
    import spark.sqlContext.implicits._

    val df: DataFrame = spark.sparkContext
      .parallelize(List(
        ("A", 1, false),
        ("B", 2, true)
      ))
      .toDF("name", "number", "isGreat")

    val outputDf = MySparkBatchExample1.transformData(df)

    if (IS_PRINTING_ENABLED) outputDf.printSchema()
    if (IS_PRINTING_ENABLED) outputDf.show(IS_SHORT_OUTPUT_ENABLED)

    outputDf.schema.size mustEqual df.schema.size+1
  }

}