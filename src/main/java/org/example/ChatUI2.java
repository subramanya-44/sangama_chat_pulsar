package org.example;


import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatUI2 extends JFrame {

    private static final String TOPIC = "persistent://public/default/chat-topic";
    private static final String SERVICE_URL = "pulsar://localhost:6650";

    private JTextArea messageArea;
    private JTextArea chatArea;
    private JButton sendButton;
    private PulsarClient client;
    private Producer<byte[]> producer;
    private Consumer<byte[]> consumer;

    public ChatUI2() {
        // Set up the frame
        setTitle("Chat Application - Entity 2");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add components
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        messageArea = new JTextArea(3, 40);
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        // Set up Pulsar client, producer, and consumer
        try {
            client = PulsarClient.builder()
                    .serviceUrl(SERVICE_URL)
                    .build();

            producer = client.newProducer()
                    .topic(TOPIC)
                    .create();

            consumer = client.newConsumer()
                    .topic(TOPIC)
                    .subscriptionName("chat-subscription-entity2")
                    .subscribe();

            // Set up message sending
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendMessage();
                }
            });

            // Start the consumer thread
            new Thread(() -> {
                try {
                    while (true) {
                        Message<byte[]> msg = consumer.receive();
                        String receivedMessage = new String(msg.getData());
                        chatArea.append("Entity 1 says: " + receivedMessage + "\n");
                        consumer.acknowledge(msg);
                    }
                } catch (PulsarClientException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            String message = messageArea.getText().trim();
            if (!message.isEmpty()) {
                producer.send(message.getBytes());
                chatArea.append("Entity 2 (you) says: " + message + "\n");
                messageArea.setText("");
            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatUI2 ui = new ChatUI2();
            ui.setVisible(true);
        });
    }
}
