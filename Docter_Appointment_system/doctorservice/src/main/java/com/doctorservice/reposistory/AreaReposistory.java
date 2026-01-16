package com.doctorservice.reposistory;

import com.doctorservice.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AreaReposistory extends JpaRepository<Area,Long> {
    Optional<Area> findFirstByNameIgnoreCase(String name);
}
