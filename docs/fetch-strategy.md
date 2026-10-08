# Stratégie de Fetch et Cascade - Projet AutoLoc

Ce document justifie les choix de stratégies de chargement (Fetch) et de propagation (Cascade) pour les associations JPA du modèle AutoLoc.

## Tableau récapitulatif

| Association | Fetch | Cascade | Justification |
| :--- | :--- | :--- | :--- |
| **Contrat → Paiement** | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition). Supprimer le contrat doit supprimer ses paiements. Retirer un paiement de la liste doit le supprimer en base. |
| **Agence → Vehicule** | LAZY | Aucune | Un véhicule peut être transféré à une autre agence ou vendu. Il ne doit pas être supprimé si l'agence est supprimée. |
| **Agence → Employee** | LAZY | Aucune | Un employé peut être muté ou démissionner. Sa suppression ne doit pas être liée à celle de l'agence. |
| **Vehicle ↔ Equipement** | LAZY | Aucune | Les équipements (GPS, siège bébé) sont partagés entre plusieurs véhicules. Aucune cascade de suppression n'est logique. |
| **Client → Reservation** | LAZY | PERSIST | Si on enregistre un nouveau client avec ses réservations, on veut que celles-ci soient sauvegardées en même temps. Mais on ne veut pas supprimer les réservations si le client est supprimé (historique). |
| **Reservation → Vehicle** | LAZY | Aucune | Une réservation fait référence à un véhicule. La suppression d'une réservation ne doit pas supprimer le véhicule. |
| **Reservation ↔ Contrat** | LAZY | ALL (côté Reservation) | Un contrat est généré à partir d'une réservation. Si on supprime la réservation, le contrat associé doit aussi être supprimé (cascade ALL). |
| **Vehicle → Maintenance** | LAZY | PERSIST | Si on ajoute une maintenance à un véhicule et qu'on sauvegarde le véhicule, la maintenance doit être enregistrée. Mais on ne supprime pas les maintenances si le véhicule est supprimé (historique). |

## Explication des choix

- **LAZY** : Toutes les associations utilisent le chargement paresseux pour éviter de charger inutilement de grandes quantités de données. Par exemple, charger un `Contrat` ne doit pas charger automatiquement tous ses `Paiements` si on ne les utilise pas.
- **Cascade ALL + orphanRemoval** : Réservé à la composition forte (Contrat/Paiement). C'est le seul cas où l'enfant ne peut pas exister sans le parent.
- **Cascade PERSIST** : Utilisé pour propager la sauvegarde (ex: Client -> Reservation, Vehicle -> Maintenance) sans propager la suppression.
- **Aucune cascade** : Pour les relations où les entités ont un cycle de vie indépendant (Agence/Vehicule, Vehicle/Equipement).