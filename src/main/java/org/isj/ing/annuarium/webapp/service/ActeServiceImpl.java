package org.isj.ing.annuarium.webapp.service;

import org.isj.ing.annuarium.webapp.mapper.ActeMapper;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.entities.Acte;
import org.isj.ing.annuarium.webapp.repository.ActeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ActeServiceImpl implements IActe{
    //injection des dependances
    @Autowired
    ActeRepository acteRepository;
    @Autowired
    ActeMapper acteMapper;

    @Override
    public int saveActe(ActeDto acteDto) {
         if(acteRepository.findActeByNumero(acteDto.getNumero()).isPresent()){
             return 0;
         }
         else {
            return acteRepository.save(acteMapper.toEntity(acteDto)).getId().intValue();
         }
    }

    @Override
    public ActeDto searchActeByNumero(String numero) {
        //retourne l'entite , avant get retournait un optional et il faut convertir en Dto
        return acteMapper.toDto( acteRepository.findActeByNumero(numero).get());
    }

    @Override
    public List<ActeDto> listActes() {
               /*for(Acte acte : actes){
           ActeDto acteDto = acteMapper.toDto(acte);
           acteDtos.add(acteDto);
       }
        List<ActeDto> acteDtos =  new ArrayList<ActeDto>();
       return acteDtos;
       List<Acte> actes =  acteRepository.findAll();*/
       return  acteRepository.findAll().stream().map(acte -> acteMapper.toDto(acte))
               .collect(Collectors.toList());
    }

    @Override
    public int deleteActe(String numero) {
        acteRepository.deleteById(acteRepository.findActeByNumero(numero).get().getId());
        return 1;
    }

    @Override
    public List<ActeDto> searchActesBYkeyword(String keyword) {
        return acteRepository.findActeByNumeroOrNom(keyword , keyword).get().stream()
//               .map(acte -> acteMapper.toDto(acte))
                .map(acteMapper::toDto)
                .collect(Collectors.toList());
    }
}
