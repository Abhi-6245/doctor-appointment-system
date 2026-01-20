package com.doctorservice.controller;

import com.doctorservice.dto.DoctorRequestDto;
import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import com.doctorservice.entity.TimeSlots;
import com.doctorservice.reposistory.DoctorRepository;
import com.doctorservice.reposistory.TimeSlotsReposistory;
import com.doctorservice.service.DoctorService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final TimeSlotsReposistory timeSlotsReposistory;
    private final DoctorService doctorService;

    public DoctorController(
            DoctorRepository doctorRepository,
            TimeSlotsReposistory timeSlotsReposistory,
            DoctorService doctorService
    ) {
        this.doctorRepository = doctorRepository;
        this.timeSlotsReposistory = timeSlotsReposistory;
        this.doctorService = doctorService;
    }

    // ================= SAVE DOCTOR =================
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Doctor saveDoctor(
            @RequestPart("doctor") DoctorRequestDto dto,
            @RequestPart("image") MultipartFile image) {

        return doctorService.saveDoctor(dto, image);
    }



    // ================= SEARCH DOCTOR =================
    // http://localhost:8080/api/v1/doctor/search?specialization=cardiologist&areaName=btm
    @GetMapping("/search")
    public List<Doctor> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String areaName
    ) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Doctor> doctors =
                doctorRepository.findBySpecializationAndArea(specialization, areaName);

        for (Doctor doctor : doctors) {

            List<DoctorAppointmentSchedule> validSchedules = new ArrayList<>();

            for (DoctorAppointmentSchedule schedule : doctor.getAppointmentSchedules()) {

                LocalDate scheduleDate = schedule.getDate();
                List<TimeSlots> validTimeSlots = new ArrayList<>();

                List<TimeSlots> timeSlots =
                        timeSlotsReposistory.getAllTimeSlots(schedule.getId());

                for (TimeSlots ts : timeSlots) {
                    LocalTime slotTime = ts.getTime();

                    // Today → only future slots
                    if (scheduleDate.isEqual(today) && slotTime.isAfter(now)) {
                        validTimeSlots.add(ts);
                    }
                    // Future dates → all slots
                    else if (scheduleDate.isAfter(today)) {
                        validTimeSlots.add(ts);
                    }
                }

                if (!validTimeSlots.isEmpty()) {
                    schedule.setTimeSlots(validTimeSlots);
                    validSchedules.add(schedule);
                }
            }

            doctor.setAppointmentSchedules(validSchedules);
        }

        return doctors;
    }

    // ================= GET DOCTOR BY ID =================
    // http://localhost:8080/api/v1/doctor/getdoctorbyid?id=1
    @GetMapping("/getdoctorbyid")
    public Doctor getDoctorById(@RequestParam long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}
