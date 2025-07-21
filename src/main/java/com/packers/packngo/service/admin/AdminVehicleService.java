package com.packers.packngo.service.admin;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Vehicle;
import com.packers.packngo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminVehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        // Save the new vehicle to the database
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        // Retrieve all vehicles from the database
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleByLicensePlate(String licensePlate) throws ResourceNotFoundException {
        // Retrieve a vehicle by license plate or throw an exception if not found
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        return vehicle.orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with license plate: " + licensePlate));
    }

    public Vehicle updateVehicleByLicensePlate(String licensePlate, Vehicle updatedVehicle) throws ResourceNotFoundException {
        // Update the vehicle details if it exists
        Optional<Vehicle> existingVehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (existingVehicle.isPresent()) {
            Vehicle vehicle = existingVehicle.get();
            vehicle.setType(updatedVehicle.getType());
            vehicle.setPickUpLocation(updatedVehicle.getPickUpLocation());
            vehicle.setRentalPrice(updatedVehicle.getRentalPrice());
            vehicle.setLicensePlate(updatedVehicle.getLicensePlate());
            vehicle.setCapacity(updatedVehicle.getCapacity());
            vehicle.setAvailability(updatedVehicle.getAvailability());
            return vehicleRepository.save(vehicle);
        } else {
            throw new ResourceNotFoundException("Vehicle not found with license plate: " + licensePlate);
        }
    }

    public void deleteVehicleByLicensePlate(String licensePlate) throws ResourceNotFoundException {
        // Delete the vehicle if it exists
        Optional<Vehicle> existingVehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (existingVehicle.isPresent()) {
            vehicleRepository.delete(existingVehicle.get());
        } else {
            throw new ResourceNotFoundException("Vehicle not found with license plate: " + licensePlate);
        }
    }}
