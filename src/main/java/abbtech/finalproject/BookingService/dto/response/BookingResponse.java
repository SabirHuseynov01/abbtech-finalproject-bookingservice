package abbtech.finalproject.BookingService.dto.response;

public record BookingResponse(Long id,
                              Long roomId,
                              String userEmail,
                              String confirmationCode,
                              String paymentMethod,
                              Double amount) {
}
