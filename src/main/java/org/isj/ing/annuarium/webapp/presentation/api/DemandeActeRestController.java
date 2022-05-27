package org.isj.ing.annuarium.webapp.presentation.api;

import lombok.extern.slf4j.Slf4j;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.dto.DemandeActeDto;
import org.isj.ing.annuarium.webapp.presentation.controller.DemandeActeController;
import org.isj.ing.annuarium.webapp.service.IActe;
import org.isj.ing.annuarium.webapp.service.IDemandeActe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandeacte")
@Slf4j


public class DemandeActeRestController {
    @Autowired
    private IDemandeActe iDemandeActe;

    @PostMapping(value = "/save")
    public void enregistrerDemandeActe(@RequestBody DemandeActeDto create){
        DemandeActeRestController.log.info("enregistrer acte");
        iDemandeActe.saveDemandeActe(create);
    }
    @GetMapping("/all")
    public ResponseEntity<List<DemandeActeDto>> getAllActes() {
        return ResponseEntity.ok(iDemandeActe.listDemandesActe());
    }
}
