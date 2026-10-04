package tn.esprit.rany_essalah_4cce10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEquipement;

    @Column(nullable = false, length = 100)
    String libelle;

    // Association N Equipement <-> N Vehicle (Côté inverse)
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Vehicle> vehicles;
}