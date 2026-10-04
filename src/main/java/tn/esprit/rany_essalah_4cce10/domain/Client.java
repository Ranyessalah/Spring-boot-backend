package tn.esprit.rany_essalah_4cce10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClient;

    @Column(nullable = false, length = 50)
    String nom;

    @Column(nullable = false, length = 50)
    String prenom;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(nullable = false, length = 20)
    String telephone;

    @Column(nullable = false, unique = true, length = 20)
    String numPermis;

    @Column(nullable = false)
    LocalDate dateInscription;

    // Association 1 Client -> N Reservation (Côté inverse)
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Reservation> reservations;
}