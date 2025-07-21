package com.packers.packngo.service.user;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Booking;
import com.packers.packngo.model.Vehicle;
import com.packers.packngo.repository.BookingRepository;
import com.packers.packngo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserVehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Vehicle> getAllVehicles() {
        // Retrieve all vehicles from the database
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(String licensePlate) throws ResourceNotFoundException {
        // Retrieve a vehicle by ID or throw an exception if not found
        return vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + licensePlate));
    }

    public List<Vehicle> getAvailableVehicles(String startDate, String endDate) {
        // Parse the start and end dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // Fetch all bookings that overlap with the specified date range
        List<Booking> overlappingBookings = bookingRepository.findAll().stream()
                .filter(booking -> (booking.getStartDate().isBefore(end.plusDays(1)) && booking.getEndDate().isAfter(start.minusDays(1))))
                .collect(Collectors.toList());

        // Extract the IDs of booked vehicles
        Set<Long> bookedVehicleIds = overlappingBookings.stream()
                .map(booking -> booking.getVehicle().getVehicleID())
                .collect(Collectors.toSet());

        // Retrieve all vehicles that are not booked
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> !bookedVehicleIds.contains(vehicle.getVehicleID()))
                .collect(Collectors.toList());
    }
}
