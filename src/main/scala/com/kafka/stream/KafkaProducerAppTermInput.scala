package com.kafka.stream

/**
  * Created by smukherjee5 on 11/1/17.
  */

import org.apache.kafka.clients.producer._
import java.io._
import java.net.{InetAddress, ServerSocket}

object KafkaProducerAppTermInput {

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

    //listening in on 4000, this is where I ll sent input from terminal using nc
    val socket = new ServerSocket(4000).accept();

    val in = new BufferedReader (new InputStreamReader (socket.getInputStream ()))

    var count=0
    while (true)
    {
      count+=1

      val cominginText = in.readLine ()

      println(cominginText)
      println("sending message :"  + cominginText)

      val record = new ProducerRecord(topicName, "key"+count ,"test message :" + cominginText )
      println(record)
      producer.send(record)
    }

  producer.close()

   }

}
