package tn.esprit.rany_essalah_4cce10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAgence;

    @Column(nullable = false, length = 50)
    String nom;

    @Column(nullable = false, length = 50)
    String ville;

    @Column(nullable = false, length = 100)
    String adresse;

    @Column(nullable = false, length = 20)
    String telephone;

    // Association 1 Agence -> N Vehicle
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Vehicle> vehicles;

    // Association 1 Agence -> N Employee
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Employee> employees;
}