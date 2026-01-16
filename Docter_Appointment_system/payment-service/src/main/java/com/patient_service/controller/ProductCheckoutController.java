package com.patient_service.controller;


import com.patient_service.dto.ProductRequest;
import com.patient_service.dto.StripeResponse;
import com.patient_service.service.PaymentSagaService;
import com.patient_service.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController {

    private final StripeService stripeService;
    private final PaymentSagaService paymentSagaService;

    public ProductCheckoutController(
            StripeService stripeService,
            PaymentSagaService paymentSagaService) {
        this.stripeService = stripeService;
        this.paymentSagaService = paymentSagaService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(
            @RequestBody ProductRequest productRequest) {

        StripeResponse stripeResponse =
                stripeService.checkoutProducts(productRequest);

        return ResponseEntity.ok(stripeResponse);
    }

    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(
            @RequestParam("session_id") String sessionId) {

        try {
            Session session = Session.retrieve(sessionId);

            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {

                long bookingId =
                        Long.parseLong(session.getMetadata().get("bookingId"));

                // 👉 Saga handles everything
                String result =
                        paymentSagaService.handlePaymentSuccess(bookingId);

                return ResponseEntity.ok(result);
            }

            return ResponseEntity.badRequest().body("Payment not completed");

        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Stripe error");
        }
    }@GetMapping("/cancel")
    public ResponseEntity<String> handleCancel() {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body("❌ Payment failed or cancelled");
    }

}
