package tn.esprit.rany_essalah_4cce10.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMaintenance;

    @Column(nullable = false)
    LocalDate dateDebut;

    LocalDate dateFin;

    @Column(length = 255)
    String description;

    // Association N Maintenance -> 1 Vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;
}