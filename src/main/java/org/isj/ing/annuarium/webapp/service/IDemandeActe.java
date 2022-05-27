package org.isj.ing.annuarium.webapp.service;

import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.dto.DemandeActeDto;

import java.util.List;

public interface IDemandeActe {
    int saveDemandeActe(DemandeActeDto demandeActeDto);
    DemandeActeDto searchDemandeActeByNumero(String numero);
    List<DemandeActeDto> listDemandesActe();
    int deleteDemandeActe(String numero);
    List<DemandeActeDto> searchDemandesActeBYkeyword(String keyword);
}