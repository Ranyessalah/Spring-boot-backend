package tn.esprit.rany_essalah_4cce10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import tn.esprit.rany_essalah_4cce10.exception.ResourceNotFoundException;
import tn.esprit.rany_essalah_4cce10.repository.IVehicleRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;

    @Override
    public Vehicle create(Vehicle vehicle) {
        if (vehicle.getIdVehicle() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        if (vehicle.getTarifJournalier() == null || vehicle.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", id));
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle update(Long id, Vehicle vehicle) {
        Vehicle existant = findById(id);
        existant.setImmatriculation(vehicle.getImmatriculation());
        existant.setMarque(vehicle.getMarque());
        existant.setModele(vehicle.getModele());
        existant.setCategorie(vehicle.getCategorie());
        existant.setTarifJournalier(vehicle.getTarifJournalier());
        existant.setStatut(vehicle.getStatut());
        return vehicleRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", id);
        }
        vehicleRepository.deleteById(id);
    }
}