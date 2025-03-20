package abbtech.finalproject.BookingService.feign;

import abbtech.finalproject.BookingService.dto.response.HotelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service", url = "http://localhost:8082/api/hotels")
public interface HotelServiceClient {

    @GetMapping
    HotelResponse getHotelById(@PathVariable("id") Long id);
}
