package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

class KafkaProducerService {
    private val producer = KafkaProducer<String, String>(KafkaConfig.producerProps())

    fun send(topic: String, message: String) {
        val record = ProducerRecord<String, String>(topic, null, message)
        producer.send(record) { metadata, exception ->
            if (exception != null) {
                println("❌ Failed to send: ${exception.message}")
            } else {
                println("✅ Sent to ${metadata.topic()} at offset ${metadata.offset()}")
            }
        }
    }
}
