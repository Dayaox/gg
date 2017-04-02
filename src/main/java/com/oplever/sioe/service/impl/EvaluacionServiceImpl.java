package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.EvaluacionService;
import com.oplever.sioe.domain.Evaluacion;
import com.oplever.sioe.repository.EvaluacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Evaluacion.
 */
@Service
@Transactional
public class EvaluacionServiceImpl implements EvaluacionService{

    private final Logger log = LoggerFactory.getLogger(EvaluacionServiceImpl.class);
    
    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionServiceImpl(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    /**
     * Save a evaluacion.
     *
     * @param evaluacion the entity to save
     * @return the persisted entity
     */
    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        log.debug("Request to save Evaluacion : {}", evaluacion);
        Evaluacion result = evaluacionRepository.save(evaluacion);
        return result;
    }

    /**
     *  Get all the evaluacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Evaluacion> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluacions");
        Page<Evaluacion> result = evaluacionRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the evaluacions where Procedente is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Evaluacion> findAllWhereProcedenteIsNull() {
        log.debug("Request to get all evaluacions where Procedente is null");
        return StreamSupport
            .stream(evaluacionRepository.findAll().spliterator(), false)
            .filter(evaluacion -> evaluacion.getProcedente() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the evaluacions where Improcedente is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Evaluacion> findAllWhereImprocedenteIsNull() {
        log.debug("Request to get all evaluacions where Improcedente is null");
        return StreamSupport
            .stream(evaluacionRepository.findAll().spliterator(), false)
            .filter(evaluacion -> evaluacion.getImprocedente() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the evaluacions where Presentacion is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Evaluacion> findAllWherePresentacionIsNull() {
        log.debug("Request to get all evaluacions where Presentacion is null");
        return StreamSupport
            .stream(evaluacionRepository.findAll().spliterator(), false)
            .filter(evaluacion -> evaluacion.getPresentacion() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one evaluacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Evaluacion findOne(Long id) {
        log.debug("Request to get Evaluacion : {}", id);
        Evaluacion evaluacion = evaluacionRepository.findOne(id);
        return evaluacion;
    }

    /**
     *  Delete the  evaluacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluacion : {}", id);
        evaluacionRepository.delete(id);
    }
}
