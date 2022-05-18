package org.isj.ing.annuarium.webapp.presentation.api;


import lombok.extern.slf4j.Slf4j;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.service.IActe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acte")
@Slf4j
public class ActeRestController {

	@Autowired
	private IActe iActe;

	@PostMapping(value = "/save")
	public void enregistrer(@RequestBody ActeDto create){
		ActeRestController.log.info("enregistrer-acte");
		iActe.saveActe(create);
	}

	@GetMapping("/{numero}/data")
	public ResponseEntity<ActeDto> getActeByNumero(@PathVariable String numero){

		return ResponseEntity.ok(iActe.searchActeByNumero(numero));
	}


	@GetMapping("/all")
	public ResponseEntity<List<ActeDto>> getAllActes() {
		return ResponseEntity.ok(iActe.listActes());
	}

	@GetMapping("/{numero}/delete")
	public int deteleActe(@PathVariable String numero){
		return iActe.deleteActe(numero);
	}

	@GetMapping(value = "/{motcles}/search")
	public  ResponseEntity<List<ActeDto>> searchActe(@PathVariable String motcles){
		return ResponseEntity.ok(iActe.searchActesBYkeyword(motcles));
	}

}