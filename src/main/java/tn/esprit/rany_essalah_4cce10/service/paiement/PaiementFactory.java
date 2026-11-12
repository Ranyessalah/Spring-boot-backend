package tn.esprit.rany_essalah_4cce10.service.paiement;

import tn.esprit.rany_essalah_4cce10.domain.ModePaiement;
import java.util.EnumMap;
import java.util.Map;

public class PaiementFactory {
    private static final Map<ModePaiement, IModePaiement> strategies = new EnumMap<>(ModePaiement.class);

    // On enregistre les stratégies au démarrage
    static {
        strategies.put(ModePaiement.CARTE, new PaiementCarte());
        strategies.put(ModePaiement.ESPECES, new PaiementEspeces());
        strategies.put(ModePaiement.VIREMENT, new PaiementVirement());
        // Ajout du Wallet (Mission 2)
        strategies.put(ModePaiement.WALLET, new PaiementWallet());
    }

    public static IModePaiement getStrategy(ModePaiement mode) {
        IModePaiement strategy = strategies.get(mode);
        if (strategy == null) {
            throw new IllegalArgumentException("Mode de paiement non supporté : " + mode);
        }
        return strategy;
    }
}