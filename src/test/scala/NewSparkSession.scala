import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, Suite}

trait NewSparkSession extends BeforeAndAfterAll {

  self: Suite =>

  @transient private var _spark: SparkSession = _

  implicit def spark: SparkSession = _spark

  override def beforeAll() {
    _spark = SparkSession.builder()
      .master("local")
      .appName("localExecution")
      .getOrCreate()
    super.beforeAll()
  }

  override def afterAll() {
    _spark.stop()
    super.afterAll()
  }

}