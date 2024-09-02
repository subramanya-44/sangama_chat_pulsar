package org.example;

import org.apache.pulsar.client.api.*;

public class Consumer {

    public static void main(String[] args) {
        // Define Pulsar service URL
        String serviceUrl = "pulsar://localhost:6650"; // Replace with your Pulsar service URL

        // Define topic name and subscription name
        String topicName = "sangama"; // Replace with your topic name
        String subscriptionName = "my-subscription"; // Replace with your subscription name

        try {
            // Create Pulsar client
            PulsarClient client = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    .build();

            // Create consumer
            org.apache.pulsar.client.api.Consumer<byte[]> consumer = client.newConsumer()
                    .topic(topicName)
                    .subscriptionName(subscriptionName)
                    .subscriptionType(SubscriptionType.Exclusive)
                    .subscribe();

            // Receive messages
            while (true) {
                // Block until a message is received
                Message<byte[]> msg = consumer.receive();

                // Print message content
                System.out.println("Received message: " + new String(msg.getData()));

                // Acknowledge the message so that it can be deleted from the topic
                consumer.acknowledge(msg);
            }

            // Note: In a real application, you would include logic to close the consumer and client gracefully

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
