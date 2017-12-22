package com.kafka.stream

/**
  * Created by smukherjee5 on 11/1/17.
  */

import org.apache.kafka.clients.producer._
import scala.io.Source

object KafkaProducerOrder {

  def main(args: Array[String]): Unit = {

    val topicName = "test-sm1"

    //localhost:9092
    val bootstrapServer = "localhost:9092"
    //val bootstrapServer = args(1)
    val batchSize=1000
    //val timeInterval = 10


    import java.util.Properties
    val  props = new Properties()
    props.put("bootstrap.servers",bootstrapServer)

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("group.id", "my-group1")

    //I tried using these but didnt quite get it to work
    //props.put("batch.num.messages", batchSize.toString)
    //props.put("linger.ms", timeInterval.toString)


    val producer = new KafkaProducer[String, String](props)
    var cnt = 1
    var key = 1

    try {
      for (line <- Source.fromFile("/Users/smukherjee5/harvard_hw/HW9/orders.txt").getLines()) {

        //Sending order records , 1000 every second
        val record = new ProducerRecord(topicName, key.toString, line)
        println(record)
        producer.send(record)
        cnt += 1
        if (cnt == batchSize) {
          Thread.sleep(1000)
          cnt = 0
        }
        key+=1
      }
    } finally {
      producer.close()
    }

  }

}
