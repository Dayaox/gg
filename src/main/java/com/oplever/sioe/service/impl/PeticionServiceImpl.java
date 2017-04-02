package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.PeticionService;
import com.oplever.sioe.domain.Peticion;
import com.oplever.sioe.repository.PeticionRepository;
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
 * Service Implementation for managing Peticion.
 */
@Service
@Transactional
public class PeticionServiceImpl implements PeticionService{

    private final Logger log = LoggerFactory.getLogger(PeticionServiceImpl.class);
    
    private final PeticionRepository peticionRepository;

    public PeticionServiceImpl(PeticionRepository peticionRepository) {
        this.peticionRepository = peticionRepository;
    }

    /**
     * Save a peticion.
     *
     * @param peticion the entity to save
     * @return the persisted entity
     */
    @Override
    public Peticion save(Peticion peticion) {
        log.debug("Request to save Peticion : {}", peticion);
        Peticion result = peticionRepository.save(peticion);
        return result;
    }

    /**
     *  Get all the peticions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Peticion> findAll(Pageable pageable) {
        log.debug("Request to get all Peticions");
        Page<Peticion> result = peticionRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the peticions where Prevencion is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Peticion> findAllWherePrevencionIsNull() {
        log.debug("Request to get all peticions where Prevencion is null");
        return StreamSupport
            .stream(peticionRepository.findAll().spliterator(), false)
            .filter(peticion -> peticion.getPrevencion() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the peticions where Evaluacion is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Peticion> findAllWhereEvaluacionIsNull() {
        log.debug("Request to get all peticions where Evaluacion is null");
        return StreamSupport
            .stream(peticionRepository.findAll().spliterator(), false)
            .filter(peticion -> peticion.getEvaluacion() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one peticion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Peticion findOne(Long id) {
        log.debug("Request to get Peticion : {}", id);
        Peticion peticion = peticionRepository.findOne(id);
        return peticion;
    }

    /**
     *  Delete the  peticion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Peticion : {}", id);
        peticionRepository.delete(id);
    }
}
