package com.doctorservice.service;



import com.doctorservice.dto.AppointmentScheduleDto;
import com.doctorservice.dto.DoctorRequestDto;
import com.doctorservice.dto.TimeSlotDto;
import com.doctorservice.entity.*;
import com.doctorservice.reposistory.AreaReposistory;
import com.doctorservice.reposistory.CityRepository;
import com.doctorservice.reposistory.DoctorRepository;
import com.doctorservice.reposistory.StateReposistory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final StateReposistory stateRepository;
    private final CityRepository cityRepository;
    private final AreaReposistory areaRepository;
    private final S3Service s3Service;

    public DoctorService(
            DoctorRepository doctorRepository,
            StateReposistory stateRepository,
            CityRepository cityRepository,
            AreaReposistory areaRepository,
            S3Service s3Service, S3Service s3Service1) {

        this.doctorRepository = doctorRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.areaRepository = areaRepository;
        this.s3Service = s3Service1;
    }

    public Doctor saveDoctor(DoctorRequestDto dto, MultipartFile image) {

        ;  Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setQualification(dto.getQualification());
        doctor.setContact(dto.getContact());
        doctor.setExperience(dto.getExperience());
        doctor.setAddress(dto.getAddress());

        // 🔥 Upload image to S3
        String imageUrl = s3Service.uploadImage(image);
        doctor.setUrl(imageUrl);

// -------- STATE --------
        State state = stateRepository
                .findFirstByNameIgnoreCase(dto.getState().getName())
                .orElseGet(() -> {
                    State s = new State();
                    s.setName(dto.getState().getName());
                    return s;
                });
        doctor.setState(state);


// -------- CITY --------
        City city = cityRepository
                .findFirstByNameIgnoreCase(dto.getCity().getName())
                .orElseGet(() -> {
                    City c = new City();
                    c.setName(dto.getCity().getName());
                    return c;
                });
        doctor.setCity(city);


// -------- AREA --------
        Area area = areaRepository
                .findFirstByNameIgnoreCase(dto.getArea().getName())
                .orElseGet(() -> {
                    Area a = new Area();
                    a.setName(dto.getArea().getName());
                    return a;
                });
        doctor.setArea(area);

        List<DoctorAppointmentSchedule> schedules = new ArrayList<>();

        for (AppointmentScheduleDto scheduleDto : dto.getAppointmentSchedules()) {

            DoctorAppointmentSchedule schedule = new DoctorAppointmentSchedule();
            schedule.setDate(scheduleDto.getDate());
            schedule.setDoctor(doctor);

            List<TimeSlots> slots = new ArrayList<>();

            for (TimeSlotDto slotDto : scheduleDto.getTimeSlots()) {
                TimeSlots slot = new TimeSlots();
                slot.setTime(slotDto.getTime());
                slot.setDoctorAppointmentSchedule(schedule);
                slots.add(slot);
            }

            schedule.setTimeSlots(slots);
            schedules.add(schedule);
        }

        doctor.setAppointmentSchedules(schedules);

        // 🔥 SINGLE SAVE
        return doctorRepository.save(doctor);
    }
        // ---- Appointment Schedule & TimeSlots (same as before) ----
        // (no change here)

    }

