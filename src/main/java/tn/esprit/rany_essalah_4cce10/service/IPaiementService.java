package tn.esprit.rany_essalah_4cce10.service;

import tn.esprit.rany_essalah_4cce10.domain.Paiement;
import java.util.List;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}