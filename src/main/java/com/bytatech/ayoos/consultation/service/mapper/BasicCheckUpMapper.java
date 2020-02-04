package com.bytatech.ayoos.consultation.service.mapper;

import com.bytatech.ayoos.consultation.domain.*;
import com.bytatech.ayoos.consultation.service.dto.BasicCheckUpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BasicCheckUp} and its DTO {@link BasicCheckUpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BasicCheckUpMapper extends EntityMapper<BasicCheckUpDTO, BasicCheckUp> {



    default BasicCheckUp fromId(Long id) {
        if (id == null) {
            return null;
        }
        BasicCheckUp basicCheckUp = new BasicCheckUp();
        basicCheckUp.setId(id);
        return basicCheckUp;
    }
}
