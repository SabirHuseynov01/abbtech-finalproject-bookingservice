package abbtech.finalproject.BookingService.service;

import abbtech.finalproject.BookingService.dto.request.BookingRequest;
import abbtech.finalproject.BookingService.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(Long id);

}
