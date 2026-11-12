package tn.esprit.rany_essalah_4cce10.service.paiement;

import java.math.BigDecimal;

public interface IModePaiement {
    // Calcule le montant final (ex: +2% pour le wallet)
    BigDecimal calculerMontant(BigDecimal montantInitial);

    // Effectue l'action de paiement
    void payer(String reference);

    // Vérifie si ce mode permet le remboursement (demande de l'équipe 3)
    default boolean estRemboursable() {
        return true;
    }
}