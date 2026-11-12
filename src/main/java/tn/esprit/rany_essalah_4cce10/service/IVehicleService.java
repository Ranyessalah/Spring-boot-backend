package tn.esprit.rany_essalah_4cce10.service;

import java.util.List;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;

public interface IVehicleService {

    Vehicle create(Vehicle vehicle);

    Vehicle findById(Long id);

    List<Vehicle> findAll();

    Vehicle update(Long id, Vehicle vehicle);

    void deleteById(Long id);

    // Nouvelles méthodes ajoutées (Atelier 8)
    Vehicle addAndAssignAgence(Vehicle vehicle, Long idAgence);

    Vehicle assignAgence(Long idVehicle, Long idAgence);

    Vehicle assignEquipement(Long idVehicle, Long idEquipement);

    void unassignEquipement(Long idVehicle, Long idEquipement);
}