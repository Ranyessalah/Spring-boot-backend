package tn.esprit.rany_essalah_4cce10.service.paiement;

import java.math.BigDecimal;

public class PaiementCarte implements IModePaiement {
    @Override
    public BigDecimal calculerMontant(BigDecimal montantInitial) {
        return montantInitial; // Pas de frais
    }

    @Override
    public void payer(String reference) {
        System.out.println("[CARTE] Paiement de " + reference + " effectué via le réseau bancaire.");
    }
}