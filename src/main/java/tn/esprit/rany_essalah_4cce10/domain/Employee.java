package tn.esprit.rany_essalah_4cce10.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEmployee;

    @Column(nullable = false, length = 50)
    String nom;

    @Column(nullable = false, length = 50)
    String prenom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    RoleEmployee role;

    // Association N Employee -> 1 Agence (Côté propriétaire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    Agence agence;
}