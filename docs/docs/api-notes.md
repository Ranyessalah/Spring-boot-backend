# Notes sur l'API REST - Atelier 5

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
| :--- | :--- | :--- | :--- | :--- |
| `ContratController` | GET /api/contrats | `findAll()` | 200 | — |
| `ContratController` | GET /api/contrats/{id} | `findById(id)` | 200 | 404 |
| `ContratController` | POST /api/contrats | `create()` | 201 | 400 |
| `ContratController` | PUT /api/contrats/{id} | `update(id, contrat)` | 200 | 400, 404 |
| `ContratController` | DELETE /api/contrats/{id} | `deleteById(id)` | 204 | 404 |
| `PaiementController` | GET /api/paiements | `findAll()` | 200 | — |
| `PaiementController` | GET /api/paiements/{id} | `findById(id)` | 200 | 404 |
| `AgenceController` | CRUD /api/agences | — | 200/201/204 | 400/404 |
| `ClientController` | CRUD /api/clients | — | 200/201/204 | 400/404 |
| `EmployeeController` | CRUD /api/employees | — | 200/201/204 | 400/404 |
| `EquipementController` | CRUD /api/equipements | — | 200/201/204 | 400/404 |
| `MaintenanceController` | CRUD /api/maintenances | — | 200/201/204 | 400/404 |
| `ReservationController` | CRUD /api/reservations | — | 200/201/204 | 400/404 |
| `VehicleController` | CRUD /api/vehicles | — | 200/201/204 | 400/404 |

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
| :--- | :--- | :--- |
| Contrat → Paiement | `@JsonManagedReference` / `@JsonBackReference` | Composition forte : les paiements sont créés avec le contrat. |
| Agence → Vehicle | `@JsonIgnore` | On ne veut pas charger tous les véhicules d'une agence dans le JSON. |
| Client → Reservation | `@JsonIgnore` | Éviter la boucle et ne pas exposer l'historique complet. |

**Différence entre `@JsonBackReference` et `@JsonIgnore` :**
- `@JsonBackReference` : l'attribut est ignoré en sérialisation mais peut être désérialisé (utile pour la composition).
- `@JsonIgnore` : l'attribut est totalement ignoré (sérialisation et désérialisation).

## C. Postman et Swagger

- **Postman** : permet de tester les requêtes (GET, POST, PUT, DELETE) et de vérifier les codes HTTP et les corps JSON. Utile pour les cas d'erreur (404, 400).
- **Swagger UI** : génère automatiquement la documentation de l'API à partir du code. Permet d'explorer les endpoints sans écrire de requête.

**Exemple concret :** J'ai testé `POST /api/contrats` avec un corps JSON contenant deux paiements. Postman a renvoyé `201 Created` avec l'ID généré, et `GET /api/paiements` a montré les deux paiements créés par cascade.

## D. Anomalies SonarQube for IDE

1. **Anomalie 1 :** Import inutilisé dans un contrôleur — Règle `java:S1128` — Correction : suppression de l'import.
2. **Anomalie 2 :** Champ `boolean` primitif dans `Contrat` — Règle `java:S1226` — Correction : passage à `Boolean` (wrapper).

## E. Questions de compréhension

1. **Quel composant reçoit toutes les requêtes ? Comment retrouve-t-il la méthode ?**
   C'est le `DispatcherServlet`. Il utilise le `HandlerMapping` pour trouver le contrôleur et la méthode à appeler en fonction de l'URL et du verbe HTTP.

2. **Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?**
   Pour respecter l'architecture en couches : le contrôleur ne doit pas accéder directement aux données (Repository). Il dépend d'une abstraction (`IContratService`) pour permettre le remplacement de l'implémentation sans modification.

3. **Pourquoi un POST renvoie 201 et un DELETE 204 ?**
    - POST : 201 Created indique qu'une nouvelle ressource a été créée.
    - DELETE : 204 No Content indique que la suppression a réussi mais qu'il n'y a rien à renvoyer.
    - 404 : la ressource n'existe pas (géré par `ResourceNotFoundException` -> `GlobalExceptionHandler`).

4. **Pourquoi `PaiementController` n'expose-t-il que des GET ?**
   Parce qu'un paiement n'existe qu'à travers son contrat (composition). Sa création/modification/suppression passe par le `ContratController` (cascade).

5. **Quels risques présente l'exposition directe des entités JPA ?**
    - Exposition de champs internes (ex: `hibernateLazyInitializer`).
    - Boucles infinies si les associations ne sont pas annotées.
    - Couplage fort entre l'API et la base de données.
    - `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition (les paiements sont créés avec le contrat), tandis que `Agence.vehicules` utilise `@JsonIgnore` car on ne veut pas charger tous les véhicules d'une agence.