package abbtech.finalproject.BookingService.dto.response;

public record HotelResponse(Long id,
                            String name,
                            String location,
                            double pricePerNight,
                            boolean allInclusive) {
    public boolean available() {
        // Check if the hotel has any bookings within the next 30 days
        // Replace this logic with actual booking data access
        // Example: return BookingRepository.findBookingsByHotelIdAndCheckInDateBetween(id, LocalDate.now(), LocalDate.now().plusDays(30)).isEmpty();
        return true; // Placeholder for actual availability check
    }
}
