package tn.esprit.rany_essalah_4cce10.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import tn.esprit.rany_essalah_4cce10.service.IVehicleService;

@Tag(name = "Véhicules", description = "Gestion des véhicules")
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService vehicleService;

    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consultation réussie"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    @GetMapping("/{id}")
    public Vehicle findById(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Création réussie"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification réussie"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public Vehicle update(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long id,
                          @RequestBody Vehicle vehicle) {
        return vehicleService.update(id, vehicle);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long id) {
        vehicleService.deleteById(id);
    }

    @Operation(summary = "Ajouter un véhicule avec ses équipements (cascade) et l'affecter à une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Création et affectation réussies"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    @PostMapping("/agence/{idAgence}")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle addAndAssignAgence(@RequestBody Vehicle vehicle,
                                      @Parameter(description = "Identifiant (agence)") @PathVariable Long idAgence) {
        return vehicleService.addAndAssignAgence(vehicle, idAgence);
    }

    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation réussie"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")})
    @PutMapping("/{idVehicle}/agence/{idAgence}")
    public Vehicle assignAgence(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long idVehicle,
                                @Parameter(description = "Identifiant (agence)") @PathVariable Long idAgence) {
        return vehicleService.assignAgence(idVehicle, idAgence);
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation réussie"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")})
    @PutMapping("/{idVehicle}/equipements/{idEquipement}")
    public Vehicle assignEquipement(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long idVehicle,
                                    @Parameter(description = "Identifiant (équipement)") @PathVariable Long idEquipement) {
        return vehicleService.assignEquipement(idVehicle, idEquipement);
    }

    @Operation(summary = "Retirer un équipement d'un véhicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Retrait réussi"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")})
    @DeleteMapping("/{idVehicle}/equipements/{idEquipement}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unassignEquipement(@Parameter(description = "Identifiant (véhicule)") @PathVariable Long idVehicle,
                                   @Parameter(description = "Identifiant (équipement)") @PathVariable Long idEquipement) {
        vehicleService.unassignEquipement(idVehicle, idEquipement);
    }
}