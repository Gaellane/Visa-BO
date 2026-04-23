package com.projet.visa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.visa.model.Demandeur;
import com.projet.visa.model.Genre;
import com.projet.visa.model.Nationalite;
import com.projet.visa.model.StatusMarital;
import com.projet.visa.repository.DemandeurRepository;
import com.projet.visa.repository.GenreRepository;
import com.projet.visa.repository.NationaliteRepository;
import com.projet.visa.repository.StatusMaritalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeurService {
    private final DemandeurRepository demandeurRepository;
    private final StatusMaritalRepository statusMaritalRepository;
    private final GenreRepository genreRepository;
    private final NationaliteRepository nationaliteRepository;

    public Demandeur create(
        String nom,
        String prenom,
        String nomJeuneFille,
        String adresse,
        String mail,
        LocalDate dateNaissance,
        String tel,
        Integer genreId,
        Integer statusMaritalId,
        Integer nationaliteId) throws Exception {

        return saveDemandeur(null, nom, prenom, nomJeuneFille, adresse, mail, dateNaissance, tel, genreId, statusMaritalId, nationaliteId);
    }

    public Demandeur update(
        Integer id,
        String nom,
        String prenom,
        String nomJeuneFille,
        String adresse,
        String mail,
        LocalDate dateNaissance,
        String tel,
        Integer genreId,
        Integer statusMaritalId,
        Integer nationaliteId) throws Exception {

        return saveDemandeur(id, nom, prenom, nomJeuneFille, adresse, mail, dateNaissance, tel, genreId, statusMaritalId, nationaliteId);
    }

    private Demandeur saveDemandeur(
        Integer id,
        String nom,
        String prenom,
        String nomJeuneFille,
        String adresse,
        String mail,
        LocalDate dateNaissance,
        String tel,
        Integer genreId,
        Integer statusMaritalId,
        Integer nationaliteId) throws Exception {

        if(nom==null || nom.isEmpty()) 
            throw new IllegalArgumentException("Le nom est obligatoire");
        if(adresse==null || adresse.isEmpty()) 
            throw new IllegalArgumentException("L'adresse est obligatoire");
        if(tel==null || tel.isEmpty()) 
            throw new IllegalArgumentException("Le tel est obligatoire");
        if(dateNaissance==null) 
            throw new IllegalArgumentException("La date de naissance est obligatoire");

         

        Genre genre = genreRepository.findById(genreId)
            .orElseThrow(() -> new IllegalArgumentException("Genre introuvable: " + genreId));
        StatusMarital statusMarital = statusMaritalRepository.findById(statusMaritalId)
            .orElseThrow(() -> new IllegalArgumentException("Status marital introuvable: " + statusMaritalId));
        Nationalite nationalite = nationaliteRepository.findById(nationaliteId)
            .orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable: " + nationaliteId));

        Demandeur demandeur;
        if (id == null) {
            demandeur = new Demandeur();
        } else {
            demandeur = demandeurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + id));
        }

        demandeur.setNom(nom);
        demandeur.setPrenom(prenom);
        demandeur.setNomJeuneFille(nomJeuneFille);
        demandeur.setAdresse(adresse);
        demandeur.setMail(mail);
        demandeur.setDateNaissance(dateNaissance);
        demandeur.setTel(tel);
        demandeur.setGenre(genre);
        demandeur.setStatusMarital(statusMarital);
        demandeur.setNationalite(nationalite);
            
        return demandeurRepository.save(demandeur);
    }

    public List<Demandeur> findAll() {
        return demandeurRepository.findAll();
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public List<StatusMarital> findAllStatusMaritals() {
        return statusMaritalRepository.findAll();
    }

    public List<Nationalite> findAllNationalites() {
        return nationaliteRepository.findAll();
    }

    public Demandeur findById(Integer id) {
        return demandeurRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Demandeur introuvable: " + id));
    }
}
