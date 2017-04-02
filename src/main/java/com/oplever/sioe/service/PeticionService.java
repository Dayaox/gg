package com.oplever.sioe.service;

import com.oplever.sioe.domain.Peticion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Peticion.
 */
public interface PeticionService {

    /**
     * Save a peticion.
     *
     * @param peticion the entity to save
     * @return the persisted entity
     */
    Peticion save(Peticion peticion);

    /**
     *  Get all the peticions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Peticion> findAll(Pageable pageable);
    /**
     *  Get all the PeticionDTO where Prevencion is null.
     *
     *  @return the list of entities
     */
    List<Peticion> findAllWherePrevencionIsNull();
    /**
     *  Get all the PeticionDTO where Evaluacion is null.
     *
     *  @return the list of entities
     */
    List<Peticion> findAllWhereEvaluacionIsNull();

    /**
     *  Get the "id" peticion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Peticion findOne(Long id);

    /**
     *  Delete the "id" peticion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
