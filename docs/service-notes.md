# Notes sur la couche Service - Atelier 4

## A. Modes d'injection comparés

| Critère | Constructeur (retenu) | Attribut (@Autowired) |
| :--- | :--- | :--- |
| Champ final possible ? | Oui | Non |
| Dépendance visible dans la signature ? | Oui | Non |
| Utilisable avec `new` hors conteneur ? | Oui | Non (dépendance null) |
| Signalé par SonarQube ? | Non | Oui (règle S6813) |

**Conclusion :** L'injection par constructeur est la convention AutoLoc. Elle garantit que le service ne peut jamais exister dans un état incomplet.

## B. Services

| Service | Dépendances injectées | Mode d'injection | Justification |
| :--- | :--- | :--- | :--- |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `EmployeeServiceImpl` | `IEmployeeRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Service en lecture seule (pas de create/update/delete) |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Service sans état, dépendance obligatoire |
| `VehicleServiceImpl` | `IVehicleRepository` | Constructeur | Service sans état, dépendance obligatoire |

## C. Messages d'erreur du conteneur

**Message 1 :** `NoSuchBeanDefinitionException` - Le bean `IContratService` n'est pas trouvé.
- **Cause 1 :** L'annotation `@Service` est manquante sur `ContratServiceImpl`.
- **Cause 2 :** La classe `ContratServiceImpl` n'est pas dans le package scanné (elle est en dehors de `tn.esprit.rany_essalah_4cce10`).
- **Correction :** Ajouter `@Service` et vérifier l'emplacement du package.

**Message 2 :** `NoUniqueBeanDefinitionException` - Deux beans de type `INotificationService` existent (`emailNotificateur`, `smsNotificateur`).
- **Correction 1 :** Ajouter `@Primary` sur l'un des deux beans pour le désigner par défaut.
- **Correction 2 :** Utiliser `@Qualifier("emailNotificateur")` sur le paramètre du constructeur pour spécifier explicitement lequel injecter.

**Message 3 :** `BeanCurrentlyInCreationException` - Dépendance circulaire entre `ClientServiceImpl` et `ReservationServiceImpl`.
- **Pourquoi l'injection par constructeur rend ce cycle impossible à contourner ?** Parce que Spring doit créer entièrement un bean avant de pouvoir le passer à un autre. Si A dépend de B et B dépend de A, Spring ne peut pas terminer la création de l'un sans l'autre -> boucle infinie.
- **Piste de conception :** Extraire la responsabilité commune dans un troisième service (ex: `FacturationService`) dont dépendent les deux, ou utiliser `@Lazy` sur l'un des deux.

## D. Anomalies SonarQube for IDE

1. **Anomalie 1 : Injection par attribut**
    - **Règle :** `java:S6813` - Field injection should be avoided
    - **Correction :** Remplacer `@Autowired private IContratRepository repo;` par `private final IContratRepository repo;` + `@RequiredArgsConstructor`.

2. **Anomalie 2 : Exception générique**
    - **Règle :** `java:S112` - Generic exceptions should be avoided
    - **Correction :** Remplacer `throw new RuntimeException(...)` par `throw new ResourceNotFoundException(...)`.

3. **Anomalie 3 : Import inutilisé**
    - **Règle :** `java:S1128` - Unused imports should be removed
    - **Correction :** Suppression de l'import inutile.

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
   Le `new` n'existe nulle part dans notre code. C'est le conteneur Spring IoC qui instancie la classe via la réflexion Java, en appelant son constructeur.

2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
   Pour respecter le principe d'inversion de dépendance (SOLID). Le contrôleur dépend d'une abstraction, ce qui permet de changer l'implémentation sans modifier le contrôleur.

3. **Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.**
   Un singleton est partagé par tous les threads. S'il a un attribut modifiable (ex: `private Contrat contratCourant;`), deux requêtes simultanées peuvent écraser mutuellement cette valeur, provoquant des résultats incohérents.

4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` ?**
   Pour éviter d'écraser les colonnes non modifiées (notamment les collections avec `orphanRemoval`). En chargeant l'existant, on ne modifie que les champs souhaités.

5. **Différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ?**
    - `@Component` : sur une classe, détectée par le scan.
    - `@Bean` : sur une méthode de classe `@Configuration`, pour déclarer un bean qu'on ne peut pas annoter.
    - `@Primary` : sur une classe, désigne le bean par défaut en cas d'ambiguïté.
    - `@Qualifier` : sur le point d'injection, désigne explicitement le bean à injecter.
    - `IPaiementService` n'expose ni création, ni modification, ni suppression car un paiement n'existe qu'à travers son contrat (composition forte).