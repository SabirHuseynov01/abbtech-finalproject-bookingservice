package abbtech.finalproject.BookingService.kafka.producer;

import abbtech.finalproject.BookingService.kafka.event.BookingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingEventProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public BookingEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendBookingCreatedEvent(Long bookingId, String userEmail) {
        try {
            BookingEvent event = new BookingEvent(bookingId,userEmail,"BOOKING_CREATED");
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("booking-events", eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to send booking event: " + e.getMessage());
        }
    }
}
