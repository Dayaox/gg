package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.ProcedenteService;
import com.oplever.sioe.domain.Procedente;
import com.oplever.sioe.repository.ProcedenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Procedente.
 */
@Service
@Transactional
public class ProcedenteServiceImpl implements ProcedenteService{

    private final Logger log = LoggerFactory.getLogger(ProcedenteServiceImpl.class);
    
    private final ProcedenteRepository procedenteRepository;

    public ProcedenteServiceImpl(ProcedenteRepository procedenteRepository) {
        this.procedenteRepository = procedenteRepository;
    }

    /**
     * Save a procedente.
     *
     * @param procedente the entity to save
     * @return the persisted entity
     */
    @Override
    public Procedente save(Procedente procedente) {
        log.debug("Request to save Procedente : {}", procedente);
        Procedente result = procedenteRepository.save(procedente);
        return result;
    }

    /**
     *  Get all the procedentes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Procedente> findAll(Pageable pageable) {
        log.debug("Request to get all Procedentes");
        Page<Procedente> result = procedenteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one procedente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Procedente findOne(Long id) {
        log.debug("Request to get Procedente : {}", id);
        Procedente procedente = procedenteRepository.findOne(id);
        return procedente;
    }

    /**
     *  Delete the  procedente by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Procedente : {}", id);
        procedenteRepository.delete(id);
    }
}
