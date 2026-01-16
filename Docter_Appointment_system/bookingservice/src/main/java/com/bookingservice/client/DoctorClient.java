package com.bookingservice.client;

import com.bookingservice.dto.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "doctor-service",
        url = "http://localhost:8081/api/v1/doctor",
        fallback = DoctorClientFallback.class
)
public interface DoctorClient {
    @GetMapping("/getdoctorbyid")
    Doctor getDoctorById(@RequestParam("id") long id);
}
@Component
class DoctorClientFallback implements DoctorClient {
    @Override
    public Doctor getDoctorById(long id) {
        Doctor doctor = new Doctor();
        doctor.setName("Unavailable");
        doctor.setAddress("Unavailable");
        doctor.setAppointmentSchedules(List.of()); // empty list
        return doctor;
    }
}
