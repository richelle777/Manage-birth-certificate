package org.isj.ing.annuarium.webapp.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.dto.DemandeActeDto;
import org.isj.ing.annuarium.webapp.service.IActe;
import org.isj.ing.annuarium.webapp.service.IDemandeActe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class DemandeActeController {
    @Autowired
    IDemandeActe iDemandeActe;
    @Autowired
    IActe iActe;

    @GetMapping("/demandeacteform")
    public String saveDemandeActeform(Model model) {
        //affichage du formulaire
        DemandeActeDto demandeActeDto = new DemandeActeDto();
        model.addAttribute("demandeActeDto",demandeActeDto);
        return "demande";
    }
    @PostMapping("/demandeacte")
    public String saveDemandeActe(@ModelAttribute DemandeActeDto  demandeActeDto , Model model){
        List<ActeDto> acteDtos = iActe.listActes();
        ActeDto acteDto_trouve = null;
        Boolean decision = false;
        for (ActeDto acteDto : acteDtos){
            if(demandeActeDto.getNumero().equals(acteDto.getNumero()) && demandeActeDto.getNom().equals(acteDto.getNom())
                    && demandeActeDto.getLieuNaissance().equals(acteDto.getLieuNaissance())
                     ){
                acteDto_trouve = acteDto;
                decision = true;
                break;
            }
        }
        if(decision == true) {
            demandeActeDto.setNomPrenomPere(acteDto_trouve.getNomPrenomPere());
            demandeActeDto.setNomPrenomMere(acteDto_trouve.getNomPrenomMere());
            demandeActeDto.setDateNaissance(acteDto_trouve.getDateNaissance());
            iDemandeActe.saveDemandeActe(demandeActeDto);
        }
        else
            System.out.println("cet acte n'est pas present dans la base de donnees");
        return "demande";
    }

}
