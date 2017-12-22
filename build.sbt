name := "HW9"

version := "1.0"

//scalaVersion := "2.12.4"



assemblyOption in assembly ~= { _.copy(includeScala = false) }

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.0"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.1.0"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.1.0"

libraryDependencies ++= Seq("org.apache.kafka" % "kafka-clients" % "0.11.0.1"
)

assemblyJarName in assembly := "kafkaStream.jar"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}