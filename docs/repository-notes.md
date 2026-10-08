# Notes sur la couche Repository - Atelier 3

## Choix des interfaces Spring Data JPA

Pour le projet AutoLoc, nous avons choisi l'interface **`JpaRepository<Entité, Long>`** pour tous nos repositories.

### Justification

`JpaRepository` est la variante la plus complète de Spring Data JPA. Elle hérite de :
- `ListCrudRepository` : opérations CRUD avec des retours en `List` (plus pratique que `Iterable`).
- `ListPagingAndSortingRepository` : tri et pagination.
- `QueryByExampleExecutor` : requêtes par exemple.

Elle ajoute aussi des méthodes spécifiques à JPA :
- `flush()` : force l'écriture immédiate en base.
- `saveAndFlush()` : sauvegarde + flush.
- `deleteAllInBatch()` : suppression en lot (attention aux cascades !).
- `getReferenceById()` : retourne un proxy paresseux.

### Convention de nommage

Conformément aux consignes, toutes nos interfaces commencent par le préfixe **`I`** (ex: `IContratRepository`).

## Anomalies SonarQube for IDE corrigées

1.  **Anomalie 1 : Import inutilisé**
    - **Fichier :** `Vehicle.java`
    - **Règle :** `java:S1128` - Unused imports should be removed
    - **Correction :** Suppression de l'import `java.util.List` qui n'était pas utilisé.

2.  **Anomalie 2 : Utilisation de `@Data` sur une entité JPA**
    - **Fichier :** (Si applicable, sinon remplacer par une autre anomalie)
    - **Règle :** `java:S2160` - Lombok `@Data` should not be used on JPA entities
    - **Correction :** Remplacement de `@Data` par `@Getter` et `@Setter` (déjà fait à l'Atelier 1).

3.  **Anomalie 3 : Champ `boolean` primitif**
    - **Fichier :** `Contrat.java`
    - **Règle :** `java:S1226` - Boolean fields should be wrappers
    - **Correction :** Remplacement de `boolean valide` par `Boolean valide` (optionnel, mais recommandé pour les entités JPA).

## Comportement de `save()`

- Si l'identifiant est **null** : `persist()` -> INSERT.
- Si l'identifiant est **renseigné** : `merge()` -> SELECT puis UPDATE.

## Comportement de `deleteById()`

- Supprime l'entité par son identifiant.
- Applique la cascade `REMOVE` et `orphanRemoval` si configurés.
- **Attention :** `deleteAllInBatch()` ne passe pas par le contexte de persistance, donc les cascades ne s'appliquent pas !