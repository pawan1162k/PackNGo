package com.packers.packngo.controller.users;

import com.packers.packngo.model.Vehicle;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/vehicle/")
public class UserVehicleController {

    @GetMapping("/")
    public List<Vehicle> getAllVehicles() {
        return null;
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable int id) {
        return null;
    }

    @GetMapping("/available/{startDate}/{endDate}")
    public List<Vehicle> getAvailableVehicles(@PathVariable String startDate, @PathVariable String endDate) {
        return null;
    }

}
