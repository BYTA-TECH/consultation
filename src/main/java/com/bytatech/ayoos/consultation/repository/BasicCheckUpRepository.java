package com.bytatech.ayoos.consultation.repository;

import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BasicCheckUp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasicCheckUpRepository extends JpaRepository<BasicCheckUp, Long> {

}
