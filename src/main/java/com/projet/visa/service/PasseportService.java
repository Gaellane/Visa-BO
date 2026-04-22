package com.projet.visa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.visa.model.Demandeur;
import com.projet.visa.model.Passeport;
import com.projet.visa.repository.DemandeurRepository;
import com.projet.visa.repository.PasseportRepository;

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

		Demandeur demandeur = demandeurRepository.findById(demandeurId)
			.orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + demandeurId));

		Passeport passeport = new Passeport();
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
}
