package com.mycompany.app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // Kết nối đến RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Định nghĩa một hàng đợi có tên "hello"
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Gửi một message đến hàng đợi
            String message = "Hello, RabbitMQ!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            // Nhận message từ hàng đợi
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String receivedMessage = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + receivedMessage + "'");
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

            // Chờ một lúc để đảm bảo message được xử lý
            Thread.sleep(1000);
        }
    }

}
