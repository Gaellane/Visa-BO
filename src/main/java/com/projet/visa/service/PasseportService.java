package com.projet.visa.service;

import org.springframework.stereotype.Service;

import com.projet.visa.model.Passeport;
import com.projet.visa.repository.PasseportRepository;

import java.time.LocalDate;
import java.util.List;


import com.projet.visa.model.Demandeur;
import com.projet.visa.repository.DemandeurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasseportService {
	private final PasseportRepository passeportRepository;
	private final DemandeurRepository demandeurRepository;

	public Passeport create(
		String numero,
		LocalDate delivrance,
		LocalDate expiration,
		Integer demandeurId) throws Exception{

		return savePasseport(null, numero, delivrance, expiration, demandeurId);
	}

	public Passeport update(
		Integer id,
		String numero,
		LocalDate delivrance,
		LocalDate expiration) throws Exception {

		Passeport existingPasseport = passeportRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Passeport introuvable: " + id));

		return savePasseport(id, numero, delivrance, expiration, existingPasseport.getDemandeur().getId());
	}

	private Passeport savePasseport(
		Integer id,
		String numero,
		LocalDate delivrance,
		LocalDate expiration,
		Integer demandeurId) throws Exception{

		if(numero==null || numero.isEmpty())
			throw new IllegalArgumentException("Le numero est obligatoire");
		if(delivrance==null )
			throw new IllegalArgumentException("La date de delivrance est obligatoire");
		if(expiration==null)
			throw new IllegalArgumentException("La date d'expiration est obligatoire");

		Demandeur demandeur = demandeurRepository.findById(demandeurId)
			.orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeurId));

		Passeport passeport;
		if (id == null) {
			passeport = new Passeport();
		} else {
			passeport = passeportRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Passeport introuvable: " + id));
		}

		passeport.setNumero(numero);
		passeport.setDelivrance(delivrance);
		passeport.setExpiration(expiration);
		passeport.setDemandeur(demandeur);

		return passeportRepository.save(passeport);
	}

	public List<Passeport> findAll() {
		return passeportRepository.findAll();
	}

	public Passeport findById(Integer id) {
		return passeportRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Passeport introuvable: " + id));
	}

	public List<Passeport> findByDemandeur(Integer demandeurId) {
		return passeportRepository.findByDemandeur_Id(demandeurId);
	}

    public Passeport findByDemandeurId(Integer demandeurId) {
        return passeportRepository.findByDemandeurId(demandeurId).orElse(null);
    }
}
