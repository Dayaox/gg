package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Procedente;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Procedente entity.
 */
@SuppressWarnings("unused")
public interface ProcedenteRepository extends JpaRepository<Procedente,Long> {

}
