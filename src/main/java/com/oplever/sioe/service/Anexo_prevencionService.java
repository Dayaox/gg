package com.oplever.sioe.service;

import com.oplever.sioe.domain.Anexo_prevencion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Anexo_prevencion.
 */
public interface Anexo_prevencionService {

    /**
     * Save a anexo_prevencion.
     *
     * @param anexo_prevencion the entity to save
     * @return the persisted entity
     */
    Anexo_prevencion save(Anexo_prevencion anexo_prevencion);

    /**
     *  Get all the anexo_prevencions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Anexo_prevencion> findAll(Pageable pageable);

    /**
     *  Get the "id" anexo_prevencion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Anexo_prevencion findOne(Long id);

    /**
     *  Delete the "id" anexo_prevencion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
