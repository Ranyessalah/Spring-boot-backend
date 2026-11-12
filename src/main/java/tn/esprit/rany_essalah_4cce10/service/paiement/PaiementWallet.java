package tn.esprit.rany_essalah_4cce10.service.paiement;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaiementWallet implements IModePaiement {
    @Override
    public BigDecimal calculerMontant(BigDecimal montantInitial) {
        // Ajout de 2% de frais
        BigDecimal frais = montantInitial.multiply(new BigDecimal("0.02"));
        return montantInitial.add(frais).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void payer(String reference) {
        System.out.println("[WALLET] Paiement de " + reference + " effectué via le portefeuille électronique (frais 2% appliqués).");
    }
}