package tn.esprit.rany_essalah_4cce10.service;

import org.springframework.stereotype.Service;
import tn.esprit.rany_essalah_4cce10.domain.Reservation;

@Service
public class TarificationServiceImpl implements ITarificationService {

    // CORRECTION : On supprime la variable d'instance "dernierMontant"
    // Un service Singleton doit être SANS ÉTAT (Stateless).

    @Override
    public double calculerMontant(Reservation r, String codePromo) {
        // La variable est maintenant LOCALE à la méthode.
        // Elle vit uniquement le temps de l'exécution et n'est pas partagée.
        double montant = 0.0;

        // ... ici votre logique de calcul du prix (ex: nombre de jours * prix) ...
        // Exemple fictif :
        // montant = r.getVehicle().getTarifJournalier() * nombreJours;

        return montant;
    }
}