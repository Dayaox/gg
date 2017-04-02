package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.PeticionarioService;
import com.oplever.sioe.domain.Peticionario;
import com.oplever.sioe.repository.PeticionarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Peticionario.
 */
@Service
@Transactional
public class PeticionarioServiceImpl implements PeticionarioService{

    private final Logger log = LoggerFactory.getLogger(PeticionarioServiceImpl.class);
    
    private final PeticionarioRepository peticionarioRepository;

    public PeticionarioServiceImpl(PeticionarioRepository peticionarioRepository) {
        this.peticionarioRepository = peticionarioRepository;
    }

    /**
     * Save a peticionario.
     *
     * @param peticionario the entity to save
     * @return the persisted entity
     */
    @Override
    public Peticionario save(Peticionario peticionario) {
        log.debug("Request to save Peticionario : {}", peticionario);
        Peticionario result = peticionarioRepository.save(peticionario);
        return result;
    }

    /**
     *  Get all the peticionarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Peticionario> findAll(Pageable pageable) {
        log.debug("Request to get all Peticionarios");
        Page<Peticionario> result = peticionarioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one peticionario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Peticionario findOne(Long id) {
        log.debug("Request to get Peticionario : {}", id);
        Peticionario peticionario = peticionarioRepository.findOne(id);
        return peticionario;
    }

    /**
     *  Delete the  peticionario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Peticionario : {}", id);
        peticionarioRepository.delete(id);
    }
}
