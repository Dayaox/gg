package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.Anexo_evaluacionService;
import com.oplever.sioe.domain.Anexo_evaluacion;
import com.oplever.sioe.repository.Anexo_evaluacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Anexo_evaluacion.
 */
@Service
@Transactional
public class Anexo_evaluacionServiceImpl implements Anexo_evaluacionService{

    private final Logger log = LoggerFactory.getLogger(Anexo_evaluacionServiceImpl.class);
    
    private final Anexo_evaluacionRepository anexo_evaluacionRepository;

    public Anexo_evaluacionServiceImpl(Anexo_evaluacionRepository anexo_evaluacionRepository) {
        this.anexo_evaluacionRepository = anexo_evaluacionRepository;
    }

    /**
     * Save a anexo_evaluacion.
     *
     * @param anexo_evaluacion the entity to save
     * @return the persisted entity
     */
    @Override
    public Anexo_evaluacion save(Anexo_evaluacion anexo_evaluacion) {
        log.debug("Request to save Anexo_evaluacion : {}", anexo_evaluacion);
        Anexo_evaluacion result = anexo_evaluacionRepository.save(anexo_evaluacion);
        return result;
    }

    /**
     *  Get all the anexo_evaluacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Anexo_evaluacion> findAll(Pageable pageable) {
        log.debug("Request to get all Anexo_evaluacions");
        Page<Anexo_evaluacion> result = anexo_evaluacionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one anexo_evaluacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Anexo_evaluacion findOne(Long id) {
        log.debug("Request to get Anexo_evaluacion : {}", id);
        Anexo_evaluacion anexo_evaluacion = anexo_evaluacionRepository.findOne(id);
        return anexo_evaluacion;
    }

    /**
     *  Delete the  anexo_evaluacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo_evaluacion : {}", id);
        anexo_evaluacionRepository.delete(id);
    }
}
