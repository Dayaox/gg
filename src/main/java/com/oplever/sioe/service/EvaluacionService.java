package com.oplever.sioe.service;

import com.oplever.sioe.domain.Evaluacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Evaluacion.
 */
public interface EvaluacionService {

    /**
     * Save a evaluacion.
     *
     * @param evaluacion the entity to save
     * @return the persisted entity
     */
    Evaluacion save(Evaluacion evaluacion);

    /**
     *  Get all the evaluacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Evaluacion> findAll(Pageable pageable);
    /**
     *  Get all the EvaluacionDTO where Procedente is null.
     *
     *  @return the list of entities
     */
    List<Evaluacion> findAllWhereProcedenteIsNull();
    /**
     *  Get all the EvaluacionDTO where Improcedente is null.
     *
     *  @return the list of entities
     */
    List<Evaluacion> findAllWhereImprocedenteIsNull();
    /**
     *  Get all the EvaluacionDTO where Presentacion is null.
     *
     *  @return the list of entities
     */
    List<Evaluacion> findAllWherePresentacionIsNull();

    /**
     *  Get the "id" evaluacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Evaluacion findOne(Long id);

    /**
     *  Delete the "id" evaluacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
