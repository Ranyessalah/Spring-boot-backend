package tn.esprit.rany_essalah_4cce10.service.location.legacy;

import tn.esprit.rany_essalah_4cce10.domain.Contrat;
import tn.esprit.rany_essalah_4cce10.domain.ModePaiement;
import tn.esprit.rany_essalah_4cce10.domain.Reservation;
import tn.esprit.rany_essalah_4cce10.domain.Vehicle;
import tn.esprit.rany_essalah_4cce10.service.paiement.IModePaiement;
import tn.esprit.rany_essalah_4cce10.service.paiement.PaiementFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe legacy (code spaghetti) fournie pour l'atelier 6.
 * Votre mission (Equipe 2) est d'isoler la logique de paiement.
 */
public class LocationManager {

    private final SmtpClient smtpClient;
    private final SmsGateway smsGateway;

    public LocationManager(SmtpClient smtpClient, SmsGateway smsGateway) {
        this.smtpClient = smtpClient;
        this.smsGateway = smsGateway;
    }

    public Contrat confirmerReservation(Long reservationId, ModePaiement mode, String codePromo, String canal) {

        // 1. Disponibilité
        Reservation reservation = new Reservation(); // Simulation pour l'exemple
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));

        Vehicle vehicle = new Vehicle();
        vehicle.setTarifJournalier(new BigDecimal("100.00")); // Simulation

        if (!estDisponible(reservation)) {
            throw new IllegalStateException("Vehicule non disponible");
        }

        // 2. Tarif de base
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        BigDecimal tarifBase = vehicle.getTarifJournalier().multiply(BigDecimal.valueOf(jours));

        // 3. Remises
        if ("VIP".equals(codePromo)) {
            tarifBase = tarifBase.multiply(new BigDecimal("0.9")); // -10%
        }

        // 4. Paiement (PARTIE MODIFIÉE PAR L'ÉQUIPE 2)
        // On utilise la Factory pour récupérer la bonne stratégie sans if/else
        IModePaiement strategiePaiement = PaiementFactory.getStrategy(mode);

        // Le Wallet va appliquer ses 2% de frais ici
        BigDecimal montantFinal = strategiePaiement.calculerMontant(tarifBase);

        // On effectue le paiement
        strategiePaiement.payer("REF-RES-" + reservationId);

        // 5. Contrat
        Contrat contrat = new Contrat();
        contrat.setMontantTotal(montantFinal);
        contrat.setDateSignature(LocalDate.now());
        contrat.setValide(true);

        // 6. Notification
        if ("SMS".equals(canal)) {
            smsGateway.send("0600000000", "Contrat confirmé. Montant: " + montantFinal);
        } else {
            smtpClient.send("client@email.com", "Contrat confirmé. Montant: " + montantFinal);
        }

        return contrat;
    }

    private boolean estDisponible(Reservation r) {
        return true; // Simplifié pour l'atelier
    }
}