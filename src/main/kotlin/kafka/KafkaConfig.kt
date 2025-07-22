package kafka

import java.util.Properties

object KafkaConfig {
    const val BOOTSTRAP_SERVERS = "kafka:9092" // Or your Kafka server address

    fun producerProps(): Properties = Properties().apply {
        put("bootstrap.servers", BOOTSTRAP_SERVERS)
        put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    }

    fun consumerProps(groupId: String): Properties = Properties().apply {
        put("bootstrap.servers", BOOTSTRAP_SERVERS)
        put("group.id", groupId)
        put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        put("auto.offset.reset", "earliest") // or latest
    }
}


//val kafka = KafkaProducerService()
//kafka.send("order-events", "OrderPlaced:$orderId")

//val emailConsumer = KafkaConsumerService(
//    topic = "order-events",
//    groupId = "email-service"
//) { message ->
//    if (message.startsWith("OrderPlaced:")) {
//        val orderId = message.removePrefix("OrderPlaced:")
//        println("📧 Sending email for order $orderId")
//        // call email sending logic
//    }
//}
//emailConsumer.startListening()

