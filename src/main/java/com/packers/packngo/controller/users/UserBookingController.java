package com.packers.packngo.controller.users;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.dto.BookingRequestDTO;
import com.packers.packngo.model.Booking;
import com.packers.packngo.service.user.UserBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/booking/")
public class UserBookingController {

    @Autowired
    private UserBookingService userBookingService;


    @PostMapping("/")
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userBookingService.createBooking(bookingRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userBookingService.getBookingById(id));
    }

    //Get all the booking for this  user
    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings() throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userBookingService.getAllBookingsForUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking booking) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userBookingService.updateBooking(id, booking));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Booking> cancel(@PathVariable Long id) throws ResourceNotFoundException {

        return ResponseEntity.ok().body(userBookingService.cancelBooking(id));
    }
}
