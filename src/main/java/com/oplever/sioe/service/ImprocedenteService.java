package com.oplever.sioe.service;

import com.oplever.sioe.domain.Improcedente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Improcedente.
 */
public interface ImprocedenteService {

    /**
     * Save a improcedente.
     *
     * @param improcedente the entity to save
     * @return the persisted entity
     */
    Improcedente save(Improcedente improcedente);

    /**
     *  Get all the improcedentes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Improcedente> findAll(Pageable pageable);

    /**
     *  Get the "id" improcedente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Improcedente findOne(Long id);

    /**
     *  Delete the "id" improcedente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
