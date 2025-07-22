package com.commerce.utils.utils

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

// RabbitMQService.kt
object RabbitMQService {
    private val factory = ConnectionFactory().apply {
        host = "localhost" // or use env vars for flexibility
    }

    fun sendMessage(queueName: String, message: String) {
        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.queueDeclare(queueName, false, false, false, null)
                channel.basicPublish("", queueName, null, message.toByteArray())
                println("✅ Sent message: '$message'")
            }
        }
    }

    fun startConsumer(queueName: String, onMessageReceived: (String) -> Unit) {
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        channel.queueDeclare(queueName, false, false, false, null)

        val consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(tag: String?, envelope: Envelope?, props: AMQP.BasicProperties?, body: ByteArray) {
                val message = String(body)
                println("📥 Received message: $message")
                onMessageReceived(message)
            }
        }

        channel.basicConsume(queueName, true, consumer)
        println("🚀 Consumer started on queue: $queueName")
    }
}
