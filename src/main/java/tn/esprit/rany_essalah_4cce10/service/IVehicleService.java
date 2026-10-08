package tn.esprit.rany_essalah_4cce10.service;

import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import java.util.List;

public interface IVehicleService {
    Vehicle create(Vehicle vehicle);
    Vehicle findById(Long id);
    List<Vehicle> findAll();
    Vehicle update(Long id, Vehicle vehicle);
    void deleteById(Long id);
}