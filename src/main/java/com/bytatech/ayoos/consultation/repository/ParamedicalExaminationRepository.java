package com.bytatech.ayoos.consultation.repository;

import com.bytatech.ayoos.consultation.domain.ParamedicalExamination;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParamedicalExamination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamedicalExaminationRepository extends JpaRepository<ParamedicalExamination, Long> {

}
