package com.bookingservice.reposistory;

import com.bookingservice.entity.BookingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookingConfirmationReposistory extends JpaRepository<BookingConfirmation,Long> {

}
