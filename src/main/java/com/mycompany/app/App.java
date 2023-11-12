package com.mycompany.app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Queue;

public class App 
{

    private final static String QUEUE_NAME = "message 2";

    public static void main(String[] argv) throws Exception {
        // Kết nối đến RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Định nghĩa một hàng đợi có tên "hello"
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            HashMap message = new HashMap();
            message.put("name","duc");
            message.put("age","21");
            message.put("msv","283");

            channel.basicPublish("", QUEUE_NAME,false,false,null,message.toString().getBytes());

            System.out.println("Send");

        }
    }
}
