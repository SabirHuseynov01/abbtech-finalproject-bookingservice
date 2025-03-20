package abbtech.finalproject.BookingService.kafka.event;

public record BookingEvent(Long bookingId,
                           String userEmail,
                           String eventType) {
}
