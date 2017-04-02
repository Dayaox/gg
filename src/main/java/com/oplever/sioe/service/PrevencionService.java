package com.oplever.sioe.service;

import com.oplever.sioe.domain.Prevencion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Prevencion.
 */
public interface PrevencionService {

    /**
     * Save a prevencion.
     *
     * @param prevencion the entity to save
     * @return the persisted entity
     */
    Prevencion save(Prevencion prevencion);

    /**
     *  Get all the prevencions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Prevencion> findAll(Pageable pageable);

    /**
     *  Get the "id" prevencion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Prevencion findOne(Long id);

    /**
     *  Delete the "id" prevencion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
