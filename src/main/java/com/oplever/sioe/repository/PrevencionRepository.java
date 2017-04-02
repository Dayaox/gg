package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Prevencion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Prevencion entity.
 */
@SuppressWarnings("unused")
public interface PrevencionRepository extends JpaRepository<Prevencion,Long> {

}
