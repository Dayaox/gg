package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.ImprocedenteService;
import com.oplever.sioe.domain.Improcedente;
import com.oplever.sioe.repository.ImprocedenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Improcedente.
 */
@Service
@Transactional
public class ImprocedenteServiceImpl implements ImprocedenteService{

    private final Logger log = LoggerFactory.getLogger(ImprocedenteServiceImpl.class);
    
    private final ImprocedenteRepository improcedenteRepository;

    public ImprocedenteServiceImpl(ImprocedenteRepository improcedenteRepository) {
        this.improcedenteRepository = improcedenteRepository;
    }

    /**
     * Save a improcedente.
     *
     * @param improcedente the entity to save
     * @return the persisted entity
     */
    @Override
    public Improcedente save(Improcedente improcedente) {
        log.debug("Request to save Improcedente : {}", improcedente);
        Improcedente result = improcedenteRepository.save(improcedente);
        return result;
    }

    /**
     *  Get all the improcedentes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Improcedente> findAll(Pageable pageable) {
        log.debug("Request to get all Improcedentes");
        Page<Improcedente> result = improcedenteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one improcedente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Improcedente findOne(Long id) {
        log.debug("Request to get Improcedente : {}", id);
        Improcedente improcedente = improcedenteRepository.findOne(id);
        return improcedente;
    }

    /**
     *  Delete the  improcedente by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Improcedente : {}", id);
        improcedenteRepository.delete(id);
    }
}
