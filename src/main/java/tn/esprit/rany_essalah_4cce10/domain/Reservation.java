package tn.esprit.rany_essalah_4cce10.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReservation;

    @Column(nullable = false)
    LocalDate dateDebut;

    @Column(nullable = false)
    LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    StatutReservation statut;

    // Association N Reservation -> 1 Client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    Client client;

    // Association N Reservation -> 1 Vehicle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    // Association 1 Reservation -> 1 Contrat (Côté inverse)
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Contrat contrat;
}