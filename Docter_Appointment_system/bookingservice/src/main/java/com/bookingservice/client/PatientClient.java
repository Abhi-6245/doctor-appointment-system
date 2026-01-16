package com.bookingservice.client;

import com.bookingservice.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "patient-service",
        url = "http://localhost:8082/api/v1/patient",
        fallback = PatientClientFallback.class
)
public interface PatientClient {
    @GetMapping("/getpatientbyid")
    Patient getPatientById(@RequestParam long id);
}

@Component
class PatientClientFallback implements PatientClient {
    @Override
    public Patient getPatientById(long id) {
        Patient patient = new Patient();
        patient.setName("Unavailable");
        return patient;
    }
}
