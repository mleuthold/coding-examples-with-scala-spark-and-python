name := "MySparkStructuredStreaming"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0" //% "provided"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.0" //% "provided"

libraryDependencies += "com.holdenkarau" % "spark-testing-base_2.11" % "2.2.0_0.7.3" % "test"

libraryDependencies += "tv.cntt" % "netcaty_2.11" % "1.4"