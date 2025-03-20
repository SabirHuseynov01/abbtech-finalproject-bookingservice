package abbtech.finalproject.BookingService.service.impl;

import abbtech.finalproject.BookingService.dto.request.BookingRequest;
import abbtech.finalproject.BookingService.dto.response.BookingResponse;
import abbtech.finalproject.BookingService.dto.response.HotelResponse;
import abbtech.finalproject.BookingService.entity.Booking;
import abbtech.finalproject.BookingService.exception.BookingNotFoundException;
import abbtech.finalproject.BookingService.feign.HotelServiceClient;
import abbtech.finalproject.BookingService.kafka.producer.BookingEventProducer;
import abbtech.finalproject.BookingService.repository.BookingRepository;
import abbtech.finalproject.BookingService.service.BookingService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingEventProducer bookingEventProducer;
    private final HotelServiceClient hotelServiceClient;


    public BookingServiceImpl(BookingRepository bookingRepository, BookingEventProducer bookingEventProducer, HotelServiceClient hotelServiceClient) {
        this.bookingRepository = bookingRepository;
        this.bookingEventProducer = bookingEventProducer;
        this.hotelServiceClient = hotelServiceClient;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        HotelResponse hotel = hotelServiceClient.getHotelById(request.roomId());
        if (hotel == null || !hotel.available()) {
            throw new RuntimeException("Room" + request.roomId() + "is not available");
        }
        Booking booking = new Booking();
        booking.setRoomId(request.roomId());
        booking.setUserEmail(request.userEmail());
        booking.setCheckInDate(request.checkInDate());
        booking.setCheckOutDate(request.checkOutDate());
        booking.setPaymentMethod(request.paymentMethod());
        booking.setAmount(request.amount());
        booking.setConfirmationCode(UUID.randomUUID().toString());

        Booking savedBooking = bookingRepository.save(booking);
        bookingEventProducer.sendBookingCreatedEvent(savedBooking.getId(),savedBooking.getUserEmail());

        return new BookingResponse(
                savedBooking.getId(),
                savedBooking.getRoomId(),
                savedBooking.getUserEmail(),
                savedBooking.getConfirmationCode(),
                savedBooking.getPaymentMethod(),
                savedBooking.getAmount()
        );
    }

    @Override
    @Cacheable(value = "bookings", key = "#id")
    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID:" + id));
        return new BookingResponse(
                booking.getId(),
                booking.getRoomId(),
                booking.getUserEmail(),
                booking.getConfirmationCode(),
                booking.getPaymentMethod(),
                booking.getAmount()
        );
    }
}
