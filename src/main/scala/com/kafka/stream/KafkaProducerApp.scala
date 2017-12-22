package com.kafka.stream

/**
  * Created by smukherjee5 on 11/1/17.
  */

import org.apache.kafka.clients.producer._


object KafkaProducerApp {

  def main(args: Array[String]): Unit = {

    //test-sm1
    //val topicName = args(0)
    val topicName = "test-sm1"

    //localhost:9092
    val bootstrapServer = "localhost:9092"
    //val bootstrapServer = args(1)


    import java.util.Properties
    val  props = new Properties()
    props.put("bootstrap.servers",bootstrapServer)

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("group.id", "my-group1")

    val producer = new KafkaProducer[String, String](props)

    for (batch <-0 to 3)
      {println("Starting batch #" +batch )
        for (i <-0 to 4)
            {
              println("sending message #"  + i)
              val record = new ProducerRecord(topicName, "key"+ i,"test message #" + i )
              producer.send(record)
            }
          println ("Finished batch #" + batch)
          println ("Sleeping for 5 seconds ...")

        Thread.sleep(200)

      }
    producer.close()

  }




}
