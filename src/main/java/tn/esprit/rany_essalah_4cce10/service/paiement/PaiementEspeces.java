package tn.esprit.rany_essalah_4cce10.service.paiement;

import java.math.BigDecimal;

public class PaiementEspeces implements IModePaiement {
    @Override
    public BigDecimal calculerMontant(BigDecimal montantInitial) {
        return montantInitial; // Pas de frais
    }

    @Override
    public void payer(String reference) {
        System.out.println("[ESPECES] Paiement de " + reference + " reçu en main propre.");
    }

    @Override
    public boolean estRemboursable() {
        return false; // Pas de remboursement en espèces
    }
}