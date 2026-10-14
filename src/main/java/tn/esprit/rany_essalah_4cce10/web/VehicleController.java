package tn.esprit.rany_essalah_4cce10.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import tn.esprit.rany_essalah_4cce10.service.IVehicleService;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final IVehicleService vehicleService;

    @GetMapping public List<Vehicle> findAll() { return vehicleService.findAll(); }
    @GetMapping("/{id}") public Vehicle findById(@PathVariable Long id) { return vehicleService.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Vehicle create(@RequestBody Vehicle vehicle) { return vehicleService.create(vehicle); }
    @PutMapping("/{id}") public Vehicle update(@PathVariable Long id, @RequestBody Vehicle vehicle) { return vehicleService.update(id, vehicle); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteById(@PathVariable Long id) { vehicleService.deleteById(id); }
}