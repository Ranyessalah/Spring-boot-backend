package tn.esprit.rany_essalah_4cce10.service.paiement;

import java.math.BigDecimal;

public class PaiementVirement implements IModePaiement {
    @Override
    public BigDecimal calculerMontant(BigDecimal montantInitial) {
        return montantInitial;
    }

    @Override
    public void payer(String reference) {
        System.out.println("[VIREMENT] Paiement de " + reference + " en attente de réception bancaire.");
    }
}