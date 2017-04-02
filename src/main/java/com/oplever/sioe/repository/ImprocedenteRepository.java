package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Improcedente;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Improcedente entity.
 */
@SuppressWarnings("unused")
public interface ImprocedenteRepository extends JpaRepository<Improcedente,Long> {

}
