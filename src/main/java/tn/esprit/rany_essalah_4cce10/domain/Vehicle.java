package tn.esprit.rany_essalah_4cce10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idVehicle;

    @Column(nullable = false, unique = true, length = 20)
    String immatriculation;

    @Column(nullable = false, length = 50)
    String marque;

    @Column(nullable = false, length = 50)
    String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    CategorieVehicle categorie;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    StatutVehicle statut;

    // Association N Vehicle -> 1 Agence (Côté propriétaire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    Agence agence;

    // Association N Vehicle <-> N Equipement (Côté propriétaire)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicle_equipement",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    List<Equipement> equipements;

    // Association 1 Vehicle -> N Reservation
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Reservation> reservations;

    // Association 1 Vehicle -> N Maintenance
    // Cascade PERSIST : sauvegarder un véhicule sauvegarde ses nouvelles maintenances
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Maintenance> maintenances;
}