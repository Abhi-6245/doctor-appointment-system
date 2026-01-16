package com.doctorservice.dto;

import java.time.LocalDate;
import java.util.List;

public class AppointmentScheduleDto {
    private LocalDate date;
    private List<TimeSlotDto> timeSlots;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TimeSlotDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
