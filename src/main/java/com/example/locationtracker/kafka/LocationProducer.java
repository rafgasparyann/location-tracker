package com.example.locationtracker.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LocationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Random random = new Random();

    private static final String TOPIC = "location-topic";

    public void sendLocation() {
        try {
            double latitude = -90 + (90 - (-90)) * random.nextDouble();
            double longitude = -180 + (180 - (-180)) * random.nextDouble();

            LocationMessage message = new LocationMessage(latitude, longitude);
            String jsonMessage = objectMapper.writeValueAsString(message);

            kafkaTemplate.send(TOPIC, jsonMessage);
            System.out.println("Sent message: " + jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class LocationMessage {
        private double latitude;
        private double longitude;

        public LocationMessage(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }
        public double getLongitude() {
            return longitude;
        }
    }

    @Scheduled(fixedRate = 10000) // ամեն 10 վայրկյան
    public void produce() {
        sendLocation();
    }
}
