package com.patientservice.reposistory;

import com.patientservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientReposistory extends JpaRepository<Patient,Long> {
}
