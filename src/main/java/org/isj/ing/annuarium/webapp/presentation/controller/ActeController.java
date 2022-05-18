package org.isj.ing.annuarium.webapp.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.service.IActe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class ActeController {

	@Autowired
	IActe iActe;

	@GetMapping("/")
	public String pageAccueil(Model model) {

		return "index";
	}
	@GetMapping("/listactes")
	public String pageListActes(Model model) {
		//appel de la couche service pour avoir la liste des actes
		List<ActeDto> acteDtos = iActe.listActes();
		model.addAttribute("acteDtos", acteDtos);
		return "liste";
	}
	@GetMapping("/detail")
	public String pageDetail(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen
		//appel de la couche service pour avoir l'acte
		ActeDto acteDto = iActe.searchActeByNumero(numero);
		model.addAttribute("acteDto", acteDto);// c'est cette cle quon utilise pour recuperer l'objet
		return "details";
	}
	@GetMapping("/supprimer")
	public String pageSupprimer(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen
		//appel de la couche service pour avoir l'acte
		iActe.deleteActe(numero);
		return "redirect:/listactes";
	}
	@GetMapping("/enregistreracteform")
	public String enregistrerActeForm(Model model) { //vient a l'examen
		//affichage du formulaire
			ActeDto acteDto = new ActeDto();
			acteDto.setNumero("CM");
			model.addAttribute("acteDto",acteDto);
			return "enregistrer";
	}
	// Traitemement des valeurs saisies dans le formulaire
	@PostMapping("/enregistreracte")
	public String enregistrerActe(@ModelAttribute ActeDto acteDto, Model model){

		ActeController.log.info("enregistrer-acte");
		// appel de la couche service ou metier injectée pour enregistrer un materiel
		iActe.saveActe(acteDto);

		return "redirect:/listactes";
	}

	@GetMapping("/searchacteform")
	public String searchActeform(Model model) {
		//appel de la couche service pour avoir la liste des actes
		List<ActeDto> acteDtos = iActe.listActes();
		model.addAttribute("acteDtos", acteDtos);
		return "rechercher";
	}
	@PostMapping ("/searchacte")
	public String searchActe(@RequestParam(name = "motcle") String motcle ,Model model){

		ActeController.log.info("enregistrer-acte");
		// appel de la couche service ou metier injectée pour enregistrer un materiel
		List<ActeDto> acteDtos = iActe.searchActesBYkeyword(motcle);
		model.addAttribute("acteDtos", acteDtos);
		return "rechercher";
	}

//	@GetMapping("/editer")
//	public String editerActe(Model model) { //vient a l'examen
//		//affichage du formulaire
//		ActeDto acteDto = new ActeDto();
//		acteDto.setNumero("CM");
//		model.addAttribute("acteDto",acteDto);
//		return "editer";
//	}
	@GetMapping("/editeracteform")
	public String editerActe(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen
		//appel de la couche service pour avoir l'acte
		ActeDto acteDto = iActe.searchActeByNumero(numero);
		acteDto.setNomPrenomMere("testons voir");
		ActeDto acteDto1 = new ActeDto();
		acteDto1.setNumero(numero);
		acteDto1.setNom(acteDto.getNom());
		acteDto1.setPrenom(acteDto.getPrenom());
		acteDto1.setDateNaissance(acteDto.getDateNaissance());
		acteDto1.setLieuNaissance(acteDto.getLieuNaissance());
		acteDto1.setNomPrenomPere(acteDto.getNomPrenomPere());
		acteDto1.setNomPrenomMere(acteDto.getNomPrenomMere());
		model.addAttribute("acteDto", acteDto);// c'est cette cle quon utilise pour recuperer l'objet
		return "editer";
	}
	// Traitemement des valeurs saisies dans le formulaire
	@PostMapping("/editeracte")
	public String editerActe(@ModelAttribute ActeDto acteDto, Model model){

		ActeController.log.info("enregistrer-acte");
		// appel de la couche service ou metier injectée pour enregistrer un materiel
		iActe.saveActe(acteDto);

		return "redirect:/details";
	}
}
