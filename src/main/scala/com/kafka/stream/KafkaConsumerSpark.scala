package com.kafka.stream

/**
  * Created by smukherjee5 on 11/1/17.
  */

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.common.serialization.StringDeserializer


object KafkaConsumerSpark {

  def main(args: Array[String]): Unit = {

    //val Array(brokers, topics) = args
    //val topicsSet = topics.split(",").toSet
    //val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    //test-sm4
    val topicName = "test-sm1"
    //val topicName = args(0)
    val bootstrapServer="localhost:9092"

    val conf = new SparkConf().setMaster("local[2]").setAppName("AQuickExample")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> bootstrapServer,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group-4",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val ssc = new StreamingContext(conf, Seconds(1))

    val inputStream = KafkaUtils.createDirectStream[String,String](ssc, PreferConsistent, Subscribe[String, String](Array(topicName), kafkaParams))

    val lines = inputStream.map(_.value)
                              .map(x=>x.split(","))
                                .filter(x=>x(6).equals("B"))
                                  .map(x=> (x(3),x(4)))
                                    .reduceByKey(_+_)

    lines.print()

    ssc.start()
    ssc.awaitTermination()


  }

}

