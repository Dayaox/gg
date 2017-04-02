package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.Anexo_prevencionService;
import com.oplever.sioe.domain.Anexo_prevencion;
import com.oplever.sioe.repository.Anexo_prevencionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Anexo_prevencion.
 */
@Service
@Transactional
public class Anexo_prevencionServiceImpl implements Anexo_prevencionService{

    private final Logger log = LoggerFactory.getLogger(Anexo_prevencionServiceImpl.class);
    
    private final Anexo_prevencionRepository anexo_prevencionRepository;

    public Anexo_prevencionServiceImpl(Anexo_prevencionRepository anexo_prevencionRepository) {
        this.anexo_prevencionRepository = anexo_prevencionRepository;
    }

    /**
     * Save a anexo_prevencion.
     *
     * @param anexo_prevencion the entity to save
     * @return the persisted entity
     */
    @Override
    public Anexo_prevencion save(Anexo_prevencion anexo_prevencion) {
        log.debug("Request to save Anexo_prevencion : {}", anexo_prevencion);
        Anexo_prevencion result = anexo_prevencionRepository.save(anexo_prevencion);
        return result;
    }

    /**
     *  Get all the anexo_prevencions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Anexo_prevencion> findAll(Pageable pageable) {
        log.debug("Request to get all Anexo_prevencions");
        Page<Anexo_prevencion> result = anexo_prevencionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one anexo_prevencion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Anexo_prevencion findOne(Long id) {
        log.debug("Request to get Anexo_prevencion : {}", id);
        Anexo_prevencion anexo_prevencion = anexo_prevencionRepository.findOne(id);
        return anexo_prevencion;
    }

    /**
     *  Delete the  anexo_prevencion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo_prevencion : {}", id);
        anexo_prevencionRepository.delete(id);
    }
}
