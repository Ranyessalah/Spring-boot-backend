package tn.esprit.rany_essalah_4cce10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idContrat;

    @Column(nullable = false)
    LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal montantTotal;

    @Column(nullable = false)
    boolean valide;

    // Association 1 Contrat -> 1 Reservation (Côté propriétaire)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    Reservation reservation;

    // Association 1 Contrat -> N Paiement (Cascade ALL)
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Paiement> paiements;
}