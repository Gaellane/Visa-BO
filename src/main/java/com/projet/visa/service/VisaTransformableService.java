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

        return saveVisaTransformable(null, dateEntree, reference, lieu, expiration, passeportId);
    }

    public VisaTransformable update(
        Integer id,
        LocalDate dateEntree,
        String reference,
        String lieu,
        LocalDate expiration) throws Exception {

        VisaTransformable existing = visaTransformableRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Visa transformable introuvable: " + id));

        return saveVisaTransformable(id, dateEntree, reference, lieu, expiration, existing.getPasseport().getId());
    }

    private VisaTransformable saveVisaTransformable(
        Integer id,
        LocalDate dateEntree,
        String reference,
        String lieu,
        LocalDate expiration,
        Integer passeportId) throws Exception {

        if(dateEntree == null)
            throw new IllegalArgumentException("La date d'entree est obligatoire");
        if(reference == null || reference.isEmpty())
            throw new IllegalArgumentException("La reference est obligatoire");
        if(lieu == null || lieu.isEmpty())
            throw new IllegalArgumentException("Le lieu est obligatoire");
        if(expiration == null)
            throw new IllegalArgumentException("La date d'expiration est obligatoire");

        Passeport passeport = passeportRepository.findById(passeportId)
            .orElseThrow(() -> new IllegalArgumentException("Passeport introuvable: " + passeportId));

        VisaTransformable visaTransformable;
        if (id == null) {
            visaTransformable = new VisaTransformable();
        } else {
            visaTransformable = visaTransformableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visa transformable introuvable: " + id));
        }

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
  