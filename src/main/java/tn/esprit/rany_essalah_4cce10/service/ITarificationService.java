package tn.esprit.rany_essalah_4cce10.service;

import tn.esprit.rany_essalah_4cce10.domain.Reservation;

public interface ITarificationService {
    double calculerMontant(Reservation reservation, String codePromo);
}