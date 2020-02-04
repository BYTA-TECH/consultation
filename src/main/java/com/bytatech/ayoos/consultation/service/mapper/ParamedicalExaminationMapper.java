package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.ParamedicalExaminationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParamedicalExamination} and its DTO {@link ParamedicalExaminationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParamedicalExaminationMapper extends EntityMapper<ParamedicalExaminationDTO, ParamedicalExamination> {



    default ParamedicalExamination fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParamedicalExamination paramedicalExamination = new ParamedicalExamination();
        paramedicalExamination.setId(id);
        return paramedicalExamination;
    }
}
