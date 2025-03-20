package abbtech.finalproject.BookingService.controller;

import abbtech.finalproject.BookingService.dto.request.BookingRequest;
import abbtech.finalproject.BookingService.dto.response.BookingResponse;
import abbtech.finalproject.BookingService.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateBooking_Success() throws Exception {
        BookingRequest request = new BookingRequest(1L, "test@example.com", "2025-03-15","2025-03-20", "DEBIT_CARD",150.0);
        BookingResponse response = new BookingResponse(1L, 1L, "test@example.com", "ABC123", "DEBIT_CARD", 150.0);
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomId").value(1L))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.confirmationCode").value("ABC123"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.amount").value(150.0));

        verify(bookingService, times(1)).createBooking(any(BookingRequest.class));
    }

    @Test
    public void testGetBooking_Success() throws Exception {
        BookingResponse response = new BookingResponse(1L, 1L, "test@example.com", "ABC123", "DEBIT_CARD", 150.0);
        when(bookingService.getBooking(1L)).thenReturn(response);

        mockMvc.perform(get("/api/bookings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.roomId").value(1L))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.confirmationCode").value("ABC123"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.amount").value(150.0));

        verify(bookingService, times(1)).getBooking(1L);
    }
}
