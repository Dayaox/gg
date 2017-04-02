package com.oplever.sioe.service;

import com.oplever.sioe.domain.Anexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Anexo.
 */
public interface AnexoService {

    /**
     * Save a anexo.
     *
     * @param anexo the entity to save
     * @return the persisted entity
     */
    Anexo save(Anexo anexo);

    /**
     *  Get all the anexos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Anexo> findAll(Pageable pageable);

    /**
     *  Get the "id" anexo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Anexo findOne(Long id);

    /**
     *  Delete the "id" anexo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
