package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.ConsultationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consultation} and its DTO {@link ConsultationDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrescriptionMapper.class, DiagnosisMapper.class, BasicCheckUpMapper.class})
public interface ConsultationMapper extends EntityMapper<ConsultationDTO, Consultation> {

    @Mapping(source = "prescription.id", target = "prescriptionId")
    @Mapping(source = "diagnosis.id", target = "diagnosisId")
    @Mapping(source = "basicCheckUp.id", target = "basicCheckUpId")
    ConsultationDTO toDto(Consultation consultation);

    @Mapping(source = "prescriptionId", target = "prescription")
    @Mapping(source = "diagnosisId", target = "diagnosis")
    @Mapping(source = "basicCheckUpId", target = "basicCheckUp")
    @Mapping(target = "symptoms", ignore = true)
    @Mapping(target = "removeSymptom", ignore = true)
    Consultation toEntity(ConsultationDTO consultationDTO);

    default Consultation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultation consultation = new Consultation();
        consultation.setId(id);
        return consultation;
    }
}
