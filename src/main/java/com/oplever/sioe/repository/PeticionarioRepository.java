package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Peticionario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Peticionario entity.
 */
@SuppressWarnings("unused")
public interface PeticionarioRepository extends JpaRepository<Peticionario,Long> {

}
