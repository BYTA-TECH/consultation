package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.PrescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prescription} and its DTO {@link PrescriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescriptionMapper extends EntityMapper<PrescriptionDTO, Prescription> {



    default Prescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prescription prescription = new Prescription();
        prescription.setId(id);
        return prescription;
    }
}
