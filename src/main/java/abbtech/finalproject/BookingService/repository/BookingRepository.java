package abbtech.finalproject.BookingService.repository;

import abbtech.finalproject.BookingService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  BookingRepository extends JpaRepository<Booking,Long> {
}
