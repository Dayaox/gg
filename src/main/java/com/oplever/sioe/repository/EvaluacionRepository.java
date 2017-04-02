package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Evaluacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Evaluacion entity.
 */
@SuppressWarnings("unused")
public interface EvaluacionRepository extends JpaRepository<Evaluacion,Long> {

}
