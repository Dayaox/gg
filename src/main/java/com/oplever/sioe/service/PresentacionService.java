package com.oplever.sioe.service;

import com.oplever.sioe.domain.Presentacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Presentacion.
 */
public interface PresentacionService {

    /**
     * Save a presentacion.
     *
     * @param presentacion the entity to save
     * @return the persisted entity
     */
    Presentacion save(Presentacion presentacion);

    /**
     *  Get all the presentacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Presentacion> findAll(Pageable pageable);

    /**
     *  Get the "id" presentacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Presentacion findOne(Long id);

    /**
     *  Delete the "id" presentacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
