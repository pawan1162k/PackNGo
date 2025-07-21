package com.packers.packngo.service.admin;

import com.packers.packngo.ExceptionHandler.DatabaseAccessException;
import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Booking;
import com.packers.packngo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        try{
            return bookingRepository.findAll();
        }catch (RuntimeException e){
            throw new DatabaseAccessException("Unable to get the bookings",e);
        }
    }

    public List<Booking> getBookingsByUserId(String userId) {
        try{

            return bookingRepository.findByUsersUsername(userId);
        }catch (RuntimeException e){
            throw new DatabaseAccessException("Unable to get the bookings",e);
        }
    }

    public Booking getBookingById(Long id) throws ResourceNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            return booking.get();
        } else {
            throw new ResourceNotFoundException("Booking not found with ID: " + id);
        }
    }

    public Booking updateBookingById(Long id, Booking updatedBooking) throws ResourceNotFoundException {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();

            booking.setDetails(updatedBooking); // Update fields as needed
            booking.setStatus(updatedBooking.getStatus());
            return bookingRepository.save(booking);
        } else {
            throw new ResourceNotFoundException("Booking not found with ID: " + id);
        }
    }

    public String cancelBookingById(Long id) throws ResourceNotFoundException {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setStatus("Cancelled"); // Assuming status is a field
            bookingRepository.save(booking);
            return "Booking cancelled successfully";
        } else {
            throw new ResourceNotFoundException("Booking not found with ID: " + id);
        }
    }
}