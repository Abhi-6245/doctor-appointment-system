package com.doctorservice.dto;

import java.time.LocalTime;

public class TimeSlotDto {
    private LocalTime time;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
