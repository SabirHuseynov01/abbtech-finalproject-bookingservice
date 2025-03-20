package abbtech.finalproject.BookingService.dto.request;

public record BookingRequest(Long roomId,
                             String userEmail,
                             String checkInDate,
                             String checkOutDate,
                             String paymentMethod,
                             Double amount) {
}
