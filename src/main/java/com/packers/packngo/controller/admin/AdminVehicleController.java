package com.packers.packngo.controller.admin;

import com.packers.packngo.ExceptionHandler.ResourceNotFoundException;
import com.packers.packngo.model.Vehicle;
import com.packers.packngo.service.admin.AdminVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vehicle")
public class AdminVehicleController {

    @Autowired
    private AdminVehicleService adminVehicleService;

    @PostMapping("/")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        // Add a new vehicle logic (empty implementation)
        return ResponseEntity.ok().body(adminVehicleService.addVehicle(vehicle));
    }

    @GetMapping("/")
    public List<Vehicle> getAllVehicles() {
        // Get all vehicles logic (empty implementation)
        return adminVehicleService.getAllVehicles();
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String licensePlate) throws ResourceNotFoundException {
        // Get a specific vehicle by ID logic (empty implementation)
        return ResponseEntity.ok().body(adminVehicleService.getVehicleByLicensePlate(licensePlate));
    }

    @PutMapping("/{licensePlate}")
    public ResponseEntity<Vehicle> updateVehicleById(@PathVariable String licensePlate, @RequestBody Vehicle vehicle) throws ResourceNotFoundException {
        // Update vehicle details by ID logic (empty implementation)
        return ResponseEntity.ok().body(adminVehicleService.updateVehicleByLicensePlate(licensePlate,vehicle));
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<String> deleteVehicleById(@PathVariable String licensePlate) throws ResourceNotFoundException {
        // Delete a vehicle by ID logic (empty implementation)
        adminVehicleService.deleteVehicleByLicensePlate(licensePlate);
        return ResponseEntity.ok().body("Vehicle deleted successfully");
    }

}
