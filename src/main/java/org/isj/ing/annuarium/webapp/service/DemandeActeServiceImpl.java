package org.isj.ing.annuarium.webapp.service;

import org.isj.ing.annuarium.webapp.mapper.DemandeActeMapper;
import org.isj.ing.annuarium.webapp.model.dto.DemandeActeDto;
import org.isj.ing.annuarium.webapp.repository.DemandeActeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class DemandeActeServiceImpl implements IDemandeActe{
    //injection des dependances
    @Autowired
    DemandeActeRepository demandeActeRepository;
    @Autowired
    DemandeActeMapper demandeActeMapper;



    @Override
    public int saveDemandeActe(DemandeActeDto demandeActeDto) {
        if(!demandeActeRepository.findDemandeActeByNumero(demandeActeDto.getNumero()).isPresent()){
            return demandeActeRepository.save(demandeActeMapper.toEntity(demandeActeDto)).getId().intValue();

        }
        else {
            return 0;
        }
    }

    @Override
    public DemandeActeDto searchDemandeActeByNumero(String numero) {
        return null;
    }

    @Override
    public List<DemandeActeDto> listDemandesActe() {

        return  demandeActeRepository.findAll().stream().map(demandeActe -> demandeActeMapper.toDto(demandeActe))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteDemandeActe(String numero) {
        demandeActeRepository.deleteById(demandeActeRepository.findDemandeActeByNumero(numero).get().getId());
        return 1;
    }

    @Override
    public List<DemandeActeDto> searchDemandesActeBYkeyword(String keyword) {
        return demandeActeRepository.findDemandeActeByNumeroOrNom(keyword , keyword).get().stream()
//               .map(DemandeActe -> DemandeAacteMapper.toDto(Demandeacte))
                .map(demandeActeMapper::toDto)
                .collect(Collectors.toList());
    }

}
