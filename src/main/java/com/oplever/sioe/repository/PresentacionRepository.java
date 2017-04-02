package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Presentacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Presentacion entity.
 */
@SuppressWarnings("unused")
public interface PresentacionRepository extends JpaRepository<Presentacion,Long> {

}
