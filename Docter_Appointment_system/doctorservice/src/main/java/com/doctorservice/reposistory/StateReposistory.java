package com.doctorservice.reposistory;

import com.doctorservice.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StateReposistory  extends JpaRepository<State,Long> {
    Optional<State> findFirstByNameIgnoreCase(String name);
}
