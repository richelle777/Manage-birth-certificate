package org.isj.ing.annuarium.webapp.mapper;


import org.isj.ing.annuarium.webapp.model.dto.ActeDto;
import org.isj.ing.annuarium.webapp.model.dto.DemandeActeDto;
import org.isj.ing.annuarium.webapp.model.entities.Acte;
import org.isj.ing.annuarium.webapp.model.entities.DemandeActe;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy =  NullValueCheckStrategy.ALWAYS)
public interface DemandeActeMapper {

    DemandeActe toEntity(DemandeActeDto demandeActeDto);

    DemandeActeDto toDto(DemandeActe demandeActe);

    void copy(DemandeActeDto demandeActeDto, @MappingTarget DemandeActe demandeActe);
}
