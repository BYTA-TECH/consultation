package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.DiagnosisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Diagnosis} and its DTO {@link DiagnosisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiagnosisMapper extends EntityMapper<DiagnosisDTO, Diagnosis> {



    default Diagnosis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(id);
        return diagnosis;
    }
}
