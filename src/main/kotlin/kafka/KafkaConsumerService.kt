package kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import kotlin.concurrent.thread

class KafkaConsumerService(
    private val topic: String,
    private val groupId: String,
    private val onMessage: (String) -> Unit
) {
    private val consumer = KafkaConsumer<String, String>(KafkaConfig.consumerProps(groupId))

    fun startListening() {
        thread(start = true) {
            consumer.subscribe(listOf(topic))
            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    println("📥 Received: ${record.value()}")
                    onMessage(record.value())
                }
            }
        }
    }
}
