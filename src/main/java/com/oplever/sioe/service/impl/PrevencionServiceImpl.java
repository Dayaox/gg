package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.PrevencionService;
import com.oplever.sioe.domain.Prevencion;
import com.oplever.sioe.repository.PrevencionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Prevencion.
 */
@Service
@Transactional
public class PrevencionServiceImpl implements PrevencionService{

    private final Logger log = LoggerFactory.getLogger(PrevencionServiceImpl.class);
    
    private final PrevencionRepository prevencionRepository;

    public PrevencionServiceImpl(PrevencionRepository prevencionRepository) {
        this.prevencionRepository = prevencionRepository;
    }

    /**
     * Save a prevencion.
     *
     * @param prevencion the entity to save
     * @return the persisted entity
     */
    @Override
    public Prevencion save(Prevencion prevencion) {
        log.debug("Request to save Prevencion : {}", prevencion);
        Prevencion result = prevencionRepository.save(prevencion);
        return result;
    }

    /**
     *  Get all the prevencions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prevencion> findAll(Pageable pageable) {
        log.debug("Request to get all Prevencions");
        Page<Prevencion> result = prevencionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one prevencion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Prevencion findOne(Long id) {
        log.debug("Request to get Prevencion : {}", id);
        Prevencion prevencion = prevencionRepository.findOne(id);
        return prevencion;
    }

    /**
     *  Delete the  prevencion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prevencion : {}", id);
        prevencionRepository.delete(id);
    }
}
