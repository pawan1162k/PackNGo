package com.packers.packngo.controller.admin;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Booking;
import com.packers.packngo.service.admin.AdminBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/booking")
public class AdminBookingController {

    @Autowired
    private AdminBookingService adminBookingService;

    //Gets all the bookings in db
    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings() {
        // Get all bookings logic (empty implementation)

        System.out.println("Getting all the bookings for admin");
        return ResponseEntity.ok().body(adminBookingService.getAllBookings());
    }
    //Get the bookings by username
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable String id) {
        // Get bookings of a specific user logic (empty implementation)
        return ResponseEntity.ok().body(adminBookingService.getBookingsByUserId(id));
    }

    //Get bookings by booking id
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) throws ResourceNotFoundException {
        // Get a specific booking by ID logic (empty implementation)
        return ResponseEntity.ok().body(adminBookingService.getBookingById(id));
    }

    //update the booking by booking id
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBookingById(@RequestBody Booking booking, @PathVariable Long id) throws ResourceNotFoundException {
        // Update a booking by ID logic (empty implementation)

        return ResponseEntity.ok().body(adminBookingService.updateBookingById(id, booking));
    }

    //Cancel the booking by booking id
    @PutMapping("/cancel/{id}")
    public String cancelBookingById(@PathVariable String id) {
        // Cancel a booking by ID logic (empty implementation)
        return "Booking cancelled successfully";
    }

}
