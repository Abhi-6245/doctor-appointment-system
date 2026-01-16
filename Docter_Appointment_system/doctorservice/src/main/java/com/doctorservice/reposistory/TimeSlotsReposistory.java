package com.doctorservice.reposistory;

import com.doctorservice.entity.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeSlotsReposistory extends JpaRepository<TimeSlots,Long> {
    @Query(value = "SELECT * FROM time_slots WHERE appointment_schedule_id = :appointmentScheduleId", nativeQuery = true)
    List<TimeSlots> getAllTimeSlots(@Param("appointmentScheduleId") long appointmentScheduleId);
}
