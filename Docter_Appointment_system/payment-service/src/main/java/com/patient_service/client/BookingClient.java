package com.patient_service.client;

import com.patient_service.dto.BookingConfirmation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingClient {

    @GetMapping("/api/v1/booking/bookingId")
    BookingConfirmation getBookingById(@RequestParam("bookingId") long bookingId);

    @PutMapping("/api/v1/booking/updatestatus")
    void confirmBooking(@RequestBody BookingConfirmation bookingConfirmation);
}
