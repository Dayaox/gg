package com.oplever.sioe.service;

import com.oplever.sioe.domain.Peticionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Peticionario.
 */
public interface PeticionarioService {

    /**
     * Save a peticionario.
     *
     * @param peticionario the entity to save
     * @return the persisted entity
     */
    Peticionario save(Peticionario peticionario);

    /**
     *  Get all the peticionarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Peticionario> findAll(Pageable pageable);

    /**
     *  Get the "id" peticionario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Peticionario findOne(Long id);

    /**
     *  Delete the "id" peticionario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
