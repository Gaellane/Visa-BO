package com.projet.visa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.visa.model.Passeport;
import com.projet.visa.model.VisaTransformable;
import com.projet.visa.repository.PasseportRepository;
import com.projet.visa.repository.VisaTransformableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisaTransformableService {
    private final VisaTransformableRepository visaTransformableRepository;
    private final PasseportRepository passeportRepository;

    public VisaTransformable create(
        LocalDate dateEntree,
        String reference,
        String lieu,
        LocalDate expiration,
        Integer passeportId) throws Exception {

        Passeport passeport = passeportRepository.findById(passeportId)
            .orElseThrow(() -> new IllegalArgumentException("Passeport introuvable: " + passeportId));

        VisaTransformable visaTransformable = new VisaTransformable();
        visaTransformable.setDateEntree(dateEntree);
        visaTransformable.setReference(reference);
        visaTransformable.setLieu(lieu);
        visaTransformable.setExpiration(expiration);
        visaTransformable.setPasseport(passeport);

        return visaTransformableRepository.save(visaTransformable);
    }

    public List<VisaTransformable> findByDemandeur(Integer demandeurId) {
        return visaTransformableRepository.findByPasseport_Demandeur_Id(demandeurId);
    }

    public VisaTransformable findById(Integer id) {
        return visaTransformableRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Visa transformable introuvable: " + id));
    }
}
  