package com.bytatech.ayoos.consultation.repository;

import com.bytatech.ayoos.consultation.domain.Consultation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Consultation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
