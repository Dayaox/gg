package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Anexo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Anexo entity.
 */
@SuppressWarnings("unused")
public interface AnexoRepository extends JpaRepository<Anexo,Long> {

}
