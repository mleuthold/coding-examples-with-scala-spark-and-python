name := "MySparkExamples"

version := "0.1"

scalaVersion := "2.11.8"

// jcommander needs to be on top (first), otherwise library errors will occur
libraryDependencies += "com.beust" % "jcommander" % "1.72"

libraryDependencies += "org.scala-lang" % "scala-library" % "2.11.11"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0" //% "provided"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.0" //% "provided"

libraryDependencies += "tv.cntt" % "netcaty_2.11" % "1.4"

libraryDependencies += "com.databricks" % "spark-avro_2.11" % "3.2.0"

// for testing
libraryDependencies += "org.specs2" % "specs2-core_2.11" % "3.9.4" % "test"
libraryDependencies += "org.specs2" % "specs2-junit_2.11" % "3.9.4" % "test"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.4" % "test"
// libraryDependencies += "com.google.guava" % "guava" % "21.0"

libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.7.2"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

// https://stackoverflow.com/questions/31927051/copying-resources-using-sbt
lazy val CopyJarToRootDir = taskKey[Unit](s"CopyJarToRootDir")

copyJarToRootDir := {
  println(s"Start copying $name")
  val from = baseDirectory.value / s"target/scala-2.11/${name.value}-assembly-0.1.jar"
  val to = baseDirectory.value / s"my-jars/${name.value}-assembly-0.1.jar"
  IO.copyFile(from, to)
  println(s"$from  -> $to...done.")
}