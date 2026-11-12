package tn.esprit.rany_essalah_4cce10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.rany_essalah_4cce10.domain.Contrat;
import tn.esprit.rany_essalah_4cce10.domain.Paiement;
import tn.esprit.rany_essalah_4cce10.exception.ResourceNotFoundException;
import tn.esprit.rany_essalah_4cce10.repository.IContratRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        if (contrat.getMontantTotal() == null || contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existant = findById(id);
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        // On ne touche pas à l'id ni aux paiements
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    @Transactional // Indispensable pour que la cascade fonctionne
    public Contrat addWithPayments(Contrat contrat) {
        // 1. Vérification de base
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        if (contrat.getMontantTotal() == null || contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }

        // 2. On lie chaque paiement au contrat parent
        // C'est ce qui permet à la cascade de fonctionner (le parent gère ses enfants)
        if (contrat.getPaiements() != null && !contrat.getPaiements().isEmpty()) {
            for (Paiement paiement : contrat.getPaiements()) {
                if (paiement.getIdPaiement() != null) {
                    throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant");
                }
                if (paiement.getMontant() == null || paiement.getMontant().compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalArgumentException("Le montant du paiement ne peut pas être négatif");
                }
                // On établit le lien bidirectionnel
                paiement.setContrat(contrat);
            }
        }

        // 3. On sauvegarde le contrat (et ses paiements par cascade)
        return contratRepository.save(contrat);
    }

}