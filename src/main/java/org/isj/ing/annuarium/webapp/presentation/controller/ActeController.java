package org.isj.ing.annuarium.webapp.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


@Controller
@Slf4j
public class ActeController extends AbstractController {


	@GetMapping("/")
	public String pageAccueil(Model model) {
		getInfoUser(model);
		return "index";
	}
	@GetMapping("/listactes")
	public String pageListActes(Model model) {

		//getInfoUser(model);

		List<ActeDto> acteDtos = iActe.listActes();
		model.addAttribute("acteDtos", acteDtos);
		return "liste";
	}
	@GetMapping("/detail")
	public String pageDetail(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen

		getInfoUser(model);

		ActeDto acteDto = iActe.searchActeByNumero(numero);
		model.addAttribute("acteDto", acteDto);// c'est cette cle quon utilise pour recuperer l'objet
		return "details";
	}
	@GetMapping("/supprimer")
	public String pageSupprimer(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen

		getInfoUser(model);
		iActe.deleteActe(numero);
		return "redirect:/listactes";
	}

	@GetMapping("/enregistreracteform")
	public String enregistrerActeForm(Model model) { //vient a l'examen
		   // getInfoUser(model);
			ActeDto acteDto = new ActeDto();
			acteDto.setNumero("CM");
			model.addAttribute("acteDto",acteDto);
			return "enregistrer";
	}
	// Traitemement des valeurs saisies dans le formulaire
	@PostMapping("/enregistreracte")
	public String enregistrerActe(@ModelAttribute ActeDto acteDto, Model model){

		ActeController.log.info("enregistrer-acte");
		getInfoUser(model);
		iActe.saveActe(acteDto);

		return "redirect:/listactes";
	}

	@GetMapping("/searchacteform")
	public String searchActeform(Model model) {
		//getInfoUser(model);
		List<ActeDto> acteDtos = iActe.listActes();
		model.addAttribute("acteDtos", acteDtos);
		return "rechercher";
	}
	@PostMapping ("/searchacte")
	public String searchActe(@RequestParam(name = "motcle") String motcle ,Model model){

		ActeController.log.info("enregistrer-acte");
		//getInfoUser(model);
		List<ActeDto> acteDtos = iActe.searchActesBYkeyword(motcle);
		model.addAttribute("acteDtos", acteDtos);
		return "rechercher";
	}

	@GetMapping("/editerform")
	public String editerActeForm(@RequestParam(name = "numero") String numero,Model model) { //vient a l'examen
		//getInfoUser(model);
		ActeDto acteDto = iActe.searchActeByNumero(numero);
		model.addAttribute("acteDto",acteDto);
		return "editer";
	}
	// Traitemement des valeurs saisies dans le formulaire d'edition
	@PostMapping("/editeracte")
	public String editerActe(@ModelAttribute ActeDto acteDto, Model model){

		ActeController.log.info("editeracte");
		//getInfoUser(model);
		iActe.updateActe(acteDto);
		return "redirect:/listactes";
	}

	//code pour recuperer l'acte sous forme de pdf

	@RequestMapping("/pdf")

	public void getReportsinPDF(HttpServletResponse response) throws JRException, IOException {

		//Compiled report
		InputStream jasperStream = (InputStream) this.getClass().getResourceAsStream("/actenaissance.jasper");

		//Adding attribute names
		Map params = new HashMap<>();
		params.put("stid","stid");
		params.put("name","name");
		params.put("programme","programme");

		// Fetching the student from the data database.
		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(iActe.listActes());

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=actenaissance.pdf");

		final ServletOutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	@GetMapping("/report")
	public ResponseEntity<byte[]> generateReport(@RequestParam(value = "numero") String numero) throws FileNotFoundException, JRException {
		ActeDto acteDto = iActe.searchActeByNumero(numero);
		final byte[] data = iActe.exportReport(acteDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=actepdf.pdf");
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(data);
	}

}
