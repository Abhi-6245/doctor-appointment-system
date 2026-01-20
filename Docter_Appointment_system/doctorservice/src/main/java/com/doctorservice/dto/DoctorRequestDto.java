package com.doctorservice.dto;

import java.util.List;

public class DoctorRequestDto {
    private String name;
    private String specialization;
    private String qualification;
    private String contact;
    private String experience;
    private String address;

    private NameDto state;
    private NameDto city;
    private NameDto area;

    private List<AppointmentScheduleDto> appointmentSchedules;

    public NameDto getState() {
        return state;
    }

    public void setState(NameDto state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NameDto getCity() {
        return city;
    }

    public void setCity(NameDto city) {
        this.city = city;
    }

    public NameDto getArea() {
        return area;
    }

    public void setArea(NameDto area) {
        this.area = area;
    }

    public List<AppointmentScheduleDto> getAppointmentSchedules() {
        return appointmentSchedules;
    }

    public void setAppointmentSchedules(List<AppointmentScheduleDto> appointmentSchedules) {
        this.appointmentSchedules = appointmentSchedules;
    }

// getters & setters
}
