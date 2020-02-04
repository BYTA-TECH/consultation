package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.SymptomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Symptom} and its DTO {@link SymptomDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConsultationMapper.class})
public interface SymptomMapper extends EntityMapper<SymptomDTO, Symptom> {

    @Mapping(source = "consultation.id", target = "consultationId")
    SymptomDTO toDto(Symptom symptom);

    @Mapping(source = "consultationId", target = "consultation")
    Symptom toEntity(SymptomDTO symptomDTO);

    default Symptom fromId(Long id) {
        if (id == null) {
            return null;
        }
        Symptom symptom = new Symptom();
        symptom.setId(id);
        return symptom;
    }
}
