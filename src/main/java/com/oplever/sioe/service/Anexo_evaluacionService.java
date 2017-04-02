package com.oplever.sioe.service;

import com.oplever.sioe.domain.Anexo_evaluacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Anexo_evaluacion.
 */
public interface Anexo_evaluacionService {

    /**
     * Save a anexo_evaluacion.
     *
     * @param anexo_evaluacion the entity to save
     * @return the persisted entity
     */
    Anexo_evaluacion save(Anexo_evaluacion anexo_evaluacion);

    /**
     *  Get all the anexo_evaluacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Anexo_evaluacion> findAll(Pageable pageable);

    /**
     *  Get the "id" anexo_evaluacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Anexo_evaluacion findOne(Long id);

    /**
     *  Delete the "id" anexo_evaluacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
