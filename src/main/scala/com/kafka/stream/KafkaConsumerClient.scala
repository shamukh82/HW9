package com.kafka.stream

/**
  * Created by smukherjee5 on 11/1/17.
  */


import org.apache.kafka.clients.consumer._
import java.util.Properties
import scala.collection.JavaConverters._

object KafkaConsumerClient {


  def main(args: Array[String]): Unit = {

    //test-sm1
    val topicName = "test-sm1"
    //localhost:9092
    val bootstrapServer = "localhost:9092"

    //val topicName = args(0)
    //val bootstrapServer = args(1)

    val  props = new Properties()


    // properties for the consumer
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "my-group1")

    props.put("bootstrap.servers", bootstrapServer)


    //Add the props to the consumer and subscribes to topic
    val consumer = new KafkaConsumer[String,String](props)
    consumer.subscribe(java.util.Collections.singletonList(topicName))


    while(true) {

      val consumer_record : ConsumerRecords[String, String]  = consumer.poll(200)
      for (msg <- consumer_record.iterator().asScala)
        {
          println("msg: "+msg.value())
          println("partition: "+msg.partition())
          println("offset: "+ msg.offset())

        }
  }

    consumer.close()

  }
}
