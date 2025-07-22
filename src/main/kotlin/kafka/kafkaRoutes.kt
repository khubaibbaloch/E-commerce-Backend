package kafka

import com.commerce.config.Text
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kafka.KafkaProducerService
import kafka.KafkaConsumerService

fun Route.kafkaRoutes() {
    val topic = "test-topic"

    // Start the consumer (once per app instance)
    val consumer = KafkaConsumerService(
        topic = topic,
        groupId = "ktor-group"
    ) { message ->
        println("🔥 Kafka message handled in Ktor: $message")
    }
    consumer.startListening()

    val producer = KafkaProducerService()

    post("/kafka/send") {
        val message = call.receive<Text>()
        producer.send(topic, message.value)
        call.respondText("✅ Message sent to Kafka: $message")
    }
}
