package tn.esprit.rany_essalah_4cce10.domain;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

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
}