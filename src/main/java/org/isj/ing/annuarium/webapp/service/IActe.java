package org.isj.ing.annuarium.webapp.service;

import org.isj.ing.annuarium.webapp.model.dto.ActeDto;

import java.util.List;

public interface IActe {
    int saveActe(ActeDto acteDto);
    ActeDto searchActeByNumero(String numero);
    List<ActeDto> listActes();
    int deleteActe(String numero);
    List<ActeDto> searchActesBYkeyword(String keyword);
}
