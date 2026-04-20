package com.projet.visa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demande_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "motif")
    private String motif;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_status", nullable = false)
    private DemandeStatus status;
}
