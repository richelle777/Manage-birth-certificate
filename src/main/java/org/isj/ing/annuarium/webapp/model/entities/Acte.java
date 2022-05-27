package org.isj.ing.annuarium.webapp.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Acte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//correspondent uniquement a l'id
    private Long id;

    private String numero;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String nomPrenomPere;
    private String nomPrenomMere;

}
