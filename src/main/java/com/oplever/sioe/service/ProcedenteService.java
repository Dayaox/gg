package com.oplever.sioe.service;

import com.oplever.sioe.domain.Procedente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Procedente.
 */
public interface ProcedenteService {

    /**
     * Save a procedente.
     *
     * @param procedente the entity to save
     * @return the persisted entity
     */
    Procedente save(Procedente procedente);

    /**
     *  Get all the procedentes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Procedente> findAll(Pageable pageable);

    /**
     *  Get the "id" procedente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Procedente findOne(Long id);

    /**
     *  Delete the "id" procedente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
