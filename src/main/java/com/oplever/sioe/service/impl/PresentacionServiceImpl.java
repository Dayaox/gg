package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.PresentacionService;
import com.oplever.sioe.domain.Presentacion;
import com.oplever.sioe.repository.PresentacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Presentacion.
 */
@Service
@Transactional
public class PresentacionServiceImpl implements PresentacionService{

    private final Logger log = LoggerFactory.getLogger(PresentacionServiceImpl.class);
    
    private final PresentacionRepository presentacionRepository;

    public PresentacionServiceImpl(PresentacionRepository presentacionRepository) {
        this.presentacionRepository = presentacionRepository;
    }

    /**
     * Save a presentacion.
     *
     * @param presentacion the entity to save
     * @return the persisted entity
     */
    @Override
    public Presentacion save(Presentacion presentacion) {
        log.debug("Request to save Presentacion : {}", presentacion);
        Presentacion result = presentacionRepository.save(presentacion);
        return result;
    }

    /**
     *  Get all the presentacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Presentacion> findAll(Pageable pageable) {
        log.debug("Request to get all Presentacions");
        Page<Presentacion> result = presentacionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one presentacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Presentacion findOne(Long id) {
        log.debug("Request to get Presentacion : {}", id);
        Presentacion presentacion = presentacionRepository.findOne(id);
        return presentacion;
    }

    /**
     *  Delete the  presentacion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentacion : {}", id);
        presentacionRepository.delete(id);
    }
}
