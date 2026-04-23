package com.projet.visa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visa_transformable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisaTransformable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_entree" , nullable = false)
    private LocalDate dateEntree;

    @Column(name = "lieu" , nullable = false)
    private String lieu;

    @Column(name = "expiration" , nullable = false)
    private LocalDate expiration;

    @Column(name="reference" , nullable = false)
    private String reference;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_passeport", nullable = false)
    private Passeport passeport;
}
