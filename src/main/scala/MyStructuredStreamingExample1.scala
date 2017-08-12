import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.sql.SparkSession

object MyStructuredStreamingExample1 {

  //Logger.getLogger("org").setLevel(Level.OFF)
  @transient lazy val log = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {

    log.setLevel(Level.WARN)

    val spark = SparkSession
      .builder
      .appName("StructuredNetworkWordCount")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Create DataFrame representing the stream of input lines from connection to localhost:9999
    val lines = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()

    log.warn("Hello demo")

    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    log.warn("I am done")

    query.awaitTermination()

  }

}