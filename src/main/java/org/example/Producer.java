package org.example;
import java.util.*;
import org.apache.pulsar.client.api.*;

public class Producer {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Define Pulsar service URL
        String serviceUrl = "pulsar://localhost:6650"; // Replace with your Pulsar service URL

        // Define topic name
        String topicName = "sangama"; // Replace with your topic name

        try {
            // Create Pulsar client
            PulsarClient client = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    .build();

            // Create producer
            org.apache.pulsar.client.api.Producer<byte[]> producer = client.newProducer()
                    .topic(topicName)
                    .create();
            String a;
            // Produce messages
            for (int i = 0; i < 10; i++) {
                a = sc.nextLine();
                if (a =="exit"){
                    i = 10;
                }
                producer.send(a.getBytes());
                System.out.println("Sent message: " + a);
            }

            // Close producer and client
            producer.close();
            client.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
