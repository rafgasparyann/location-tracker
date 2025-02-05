package com.example.locationtracker.kafka;

import com.example.locationtracker.model.Location;
import com.example.locationtracker.repository.LocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationConsumer {

    @Autowired
    private LocationRepository locationRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "location-topic", groupId = "location-group")
    public void consume(String message) {
        try {
            LocationPayload payload = objectMapper.readValue(message, LocationPayload.class);
            Location location = new Location();
            location.setLatitude(payload.getLatitude());
            location.setLongitude(payload.getLongitude());
            location.setTimestamp(LocalDateTime.now());

            locationRepository.save(location);
            System.out.println("Saved location: " + location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class LocationPayload {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }


    }

}
