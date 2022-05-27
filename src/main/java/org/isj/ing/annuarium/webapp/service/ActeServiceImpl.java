package org.isj.ing.annuarium.webapp.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.isj.ing.annuarium.webapp.mapper.ActeMapper;
import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.entities.Acte;
import org.isj.ing.annuarium.webapp.repository.ActeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public ActeDto updateActe(ActeDto acteDto) {
        //recherche l'entitite qui correspond a l'acte que nous rechercons
        //update
        Acte acte = acteRepository.findActeByNumero(acteDto.getNumero()).get();
        acteMapper.copy(acteDto,acte);
        return acteMapper.toDto(acteRepository.save(acte));

    }

    @Override
    public byte[] exportReport (ActeDto acteDto) throws FileNotFoundException, JRException {
        List<ActeDto> actes = new ArrayList<>();
        actes.add(acteDto);
        //load file and compile it
        //File file = ResourceUtils.getFile("src/main/resources/actepdf.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/actepdf.jrxml"));
        // donne la source de donnée

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(actes);
        Map<String , Object> parameters = new HashMap<>();
        parameters.put("createBy" , "java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport , parameters , dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

        return data;
    }
}
