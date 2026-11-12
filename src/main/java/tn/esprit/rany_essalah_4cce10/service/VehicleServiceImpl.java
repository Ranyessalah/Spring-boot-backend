package tn.esprit.rany_essalah_4cce10.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.rany_essalah_4cce10.domain.Agence;
import tn.esprit.rany_essalah_4cce10.domain.Equipement;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import tn.esprit.rany_essalah_4cce10.exception.ResourceNotFoundException;
import tn.esprit.rany_essalah_4cce10.repository.IAgenceRepository;
import tn.esprit.rany_essalah_4cce10.repository.IEquipementRepository;
import tn.esprit.rany_essalah_4cce10.repository.IVehicleRepository;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicle create(Vehicle vehicle) {
        if (vehicle.getIdVehicle() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        valider(vehicle);
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
        valider(vehicle);
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

    @Override
    @Transactional
    public Vehicle addAndAssignAgence(Vehicle vehicle, Long idAgence) {
        if (vehicle.getIdVehicle() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        valider(vehicle);
        for (Equipement equipement : vehicle.getEquipements()) {
            if (equipement.getIdEquipement() != null) {
                throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
            }
            if (equipement.getLibelle() == null || equipement.getLibelle().isBlank()) {
                throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
            }
        }
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicle.setAgence(agence);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle assignAgence(Long idVehicle, Long idAgence) {
        Vehicle vehicle = findById(idVehicle);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicle.setAgence(agence);
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle assignEquipement(Long idVehicle, Long idEquipement) {
        Vehicle vehicle = findById(idVehicle);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicle.getEquipements().add(equipement);
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void unassignEquipement(Long idVehicle, Long idEquipement) {
        Vehicle vehicle = findById(idVehicle);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicle.getEquipements().remove(equipement);
        vehicleRepository.save(vehicle);
    }

    private void valider(Vehicle vehicle) {
        if (vehicle.getImmatriculation() == null || vehicle.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException("L'immatriculation du véhicule est obligatoire");
        }
        if (vehicle.getMarque() == null || vehicle.getMarque().isBlank()) {
            throw new IllegalArgumentException("La marque du véhicule est obligatoire");
        }
        if (vehicle.getModele() == null || vehicle.getModele().isBlank()) {
            throw new IllegalArgumentException("Le modèle du véhicule est obligatoire");
        }
        if (vehicle.getCategorie() == null) {
            throw new IllegalArgumentException("La catégorie du véhicule est obligatoire");
        }
        if (vehicle.getStatut() == null) {
            throw new IllegalArgumentException("Le statut du véhicule est obligatoire");
        }
        if (vehicle.getTarifJournalier() == null || vehicle.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier doit être renseigné et positif ou nul");
        }
    }
}