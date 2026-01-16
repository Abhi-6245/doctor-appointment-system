package com.patient_service.service;

import com.patient_service.dto.BookingConfirmation;
import org.springframework.stereotype.Service;

@Service
public class PaymentSagaService {

    private final BookingResilienceService bookingService;

    public PaymentSagaService(BookingResilienceService bookingService) {
        this.bookingService = bookingService;
    }

    public String handlePaymentSuccess(long bookingId) {

        try {
            // STEP 1: Fetch booking
            BookingConfirmation booking =
                    bookingService.getBookingById(bookingId);

            // STEP 2: Confirm booking (same logic)
            booking.setStatus(true);
            bookingService.confirmBooking(booking);

            return "Payment successful & booking confirmed";

        } catch (Exception ex) {
            // STEP 3: COMPENSATION
            compensatePayment(bookingId);
            return "Payment refunded due to booking failure";
        }
    }

    private void compensatePayment(long bookingId) {
        // refund / mark failed
        System.out.println("💸 Saga compensation triggered for bookingId: " + bookingId);
    }
}
