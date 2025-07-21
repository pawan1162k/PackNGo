package com.packers.packngo.service.user;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.dto.BookingRequestDTO;
import com.packers.packngo.model.Booking;
import com.packers.packngo.model.Transaction;
import com.packers.packngo.model.Users;
import com.packers.packngo.model.Vehicle;
import com.packers.packngo.repository.BookingRepository;
import com.packers.packngo.repository.TransactionRepository;
import com.packers.packngo.repository.UserRepository;
import com.packers.packngo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private VehicleRepository vehicleRepository;

    public Booking createBooking(BookingRequestDTO bookingRequestDTO) throws ResourceNotFoundException {
        // Set the authenticated user as the owner of the booking
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Booking booking = Booking.builder()
                .bookingDate(bookingRequestDTO.getBookingDate())
                .bookingTime(bookingRequestDTO.getBookingTime())
                .startDate(bookingRequestDTO.getStartDate())
                .endDate(bookingRequestDTO.getEndDate())
                .status(bookingRequestDTO.getStatus())
                .build();

        Transaction transaction = userTransactionService.createTransaction();
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(bookingRequestDTO.getLicensePlate());
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        if(transaction==null || vehicle.isEmpty() || user==null){
            throw new ResourceNotFoundException("user or vehicle not found");
        }
        booking.setUsers(user);
        booking.setTransaction(transaction);
        booking.setVehicle(vehicle.get());


        // Save the booking
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) throws ResourceNotFoundException {
        // Retrieve a booking by ID or throw an exception if not found
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }

    public List<Booking> getAllBookingsForUser() throws ResourceNotFoundException {
        // Retrieve the username of the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Retrieve all bookings for the user
        return bookingRepository.findByUsersId(user.getId());
    }

    public Booking updateBooking(Long id, Booking updatedBooking) throws ResourceNotFoundException {
        // Update an existing booking if it exists
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

        // Update fields as needed
        existingBooking.setStartDate(updatedBooking.getStartDate());
        existingBooking.setEndDate(updatedBooking.getEndDate());
        existingBooking.setBookingDate(updatedBooking.getBookingDate());
        existingBooking.setBookingTime(updatedBooking.getBookingTime());
        return bookingRepository.save(existingBooking);
    }

    public Booking cancelBooking(Long id) throws ResourceNotFoundException {
        // Cancel an existing booking if it exists
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

        booking.setStatus("Cancelled"); // Assuming a status field exists
        return bookingRepository.save(booking);
    }
}
