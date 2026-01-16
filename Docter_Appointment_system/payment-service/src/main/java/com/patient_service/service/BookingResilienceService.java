package com.patient_service.service;


import com.patient_service.client.BookingClient;
import com.patient_service.dto.BookingConfirmation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class BookingResilienceService {

    private final BookingClient bookingClient;

    public BookingResilienceService(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    @CircuitBreaker(name = "bookingService", fallbackMethod = "getBookingFallback")
    public BookingConfirmation getBookingById(long bookingId) {
        return bookingClient.getBookingById(bookingId);
    }

    @CircuitBreaker(name = "bookingService", fallbackMethod = "confirmBookingFallback")
    public void confirmBooking(BookingConfirmation bookingConfirmation) {
        bookingClient.confirmBooking(bookingConfirmation);
    }

    // ---------- FALLBACKS ----------
    public BookingConfirmation getBookingFallback(long bookingId, Throwable ex) {
        System.out.println("⚠️ Booking service unavailable for ID: " + bookingId);
        BookingConfirmation bc = new BookingConfirmation();
        bc.setId(bookingId);
        bc.setStatus(false);
        return bc;
    }

    public void confirmBookingFallback(BookingConfirmation booking, Throwable ex) {
        System.out.println("⚠️ Booking confirmation failed for ID: " + booking.getId() + ", will compensate via Saga");
    }
}
