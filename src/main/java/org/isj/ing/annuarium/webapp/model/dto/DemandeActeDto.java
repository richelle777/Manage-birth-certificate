package org.isj.ing.annuarium.webapp.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemandeActeDto {
    //les champs qui sont dans le dto sont l'image des champs de l'acte sans rien ajouter
    private String numero;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String lieuNaissance;
    private String nomPrenomPere;
    private String nomPrenomMere;
}
