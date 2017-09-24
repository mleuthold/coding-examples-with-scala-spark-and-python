import MyStructuredStreamingExample1.{countWords, log}
import org.apache.log4j.Level
import org.apache.spark.sql.DataFrame
import org.scalatest.FunSuite

import scala.collection.mutable.Stack

class MyStructuredStreamingExample1Spec extends FunSuite with NewSparkSession {

  log.setLevel(Level.WARN)

  ignore("start a netcat server for testing") {

    NetcatServer.async()

    NetcatServer.get("localhost", 9000, "")

  }

//  test("this is my second test") {
//    implicit val ctx = spark.sqlContext
//
//    import org.apache.spark.sql.execution.streaming.MemoryStream
//    // It uses two implicits: Encoder[Int] and SQLContext
//    val intsIn = MemoryStream[Int]
//
//    val ints = intsIn.toDF
//      .withColumn("t", current_timestamp())
//      .withWatermark("t", "5 minutes")
//      .groupBy(window($"t", "5 minutes") as "window")
//      .agg(count("*") as "total")
//
//    import org.apache.spark.sql.streaming.{OutputMode, Trigger}
//    val totalsOver5mins = ints.
//      writeStream.
//      format("memory").
//      queryName("totalsOver5mins").
//      outputMode(OutputMode.Append).
//      trigger(Trigger.ProcessingTime("10 seconds")).
//      start
//  }

  ignore("this is my test") {

    val tableName = "temporaryTable"

    // Create DataFrame representing the stream of input lines from connection to localhost:9999
    val lines: DataFrame = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    val df = countWords(lines)

    // Start running the query that prints the running counts to the console
    val query = df.writeStream
      .outputMode("complete")
      .format("memory")
      .queryName(tableName)
      .start()

    def oncePerSecond(callback: () => Unit) {
      while (true) {
        callback(); Thread sleep 5000
      }
    }

    oncePerSecond { () =>
      println(query.isActive)
      println(spark.sql(s"select * from ${tableName}").show(truncate = false))
    }

    //query.stop()

  }

  ignore("pop is invoked on a non-empty stack") {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    val oldSize = stack.size
    val result = stack.pop()
    assert(result === 2)
    assert(stack.size === oldSize - 1)
  }

  ignore("pop is invoked on an empty stack") {
    val emptyStack = new Stack[Int]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
    assert(emptyStack.isEmpty)
  }
}
