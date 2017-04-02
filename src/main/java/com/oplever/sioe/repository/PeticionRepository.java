package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Peticion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Peticion entity.
 */
@SuppressWarnings("unused")
public interface PeticionRepository extends JpaRepository<Peticion,Long> {

}
