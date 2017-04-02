package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.AnexoService;
import com.oplever.sioe.domain.Anexo;
import com.oplever.sioe.repository.AnexoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Anexo.
 */
@Service
@Transactional
public class AnexoServiceImpl implements AnexoService{

    private final Logger log = LoggerFactory.getLogger(AnexoServiceImpl.class);
    
    private final AnexoRepository anexoRepository;

    public AnexoServiceImpl(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    /**
     * Save a anexo.
     *
     * @param anexo the entity to save
     * @return the persisted entity
     */
    @Override
    public Anexo save(Anexo anexo) {
        log.debug("Request to save Anexo : {}", anexo);
        Anexo result = anexoRepository.save(anexo);
        return result;
    }

    /**
     *  Get all the anexos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Anexo> findAll(Pageable pageable) {
        log.debug("Request to get all Anexos");
        Page<Anexo> result = anexoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one anexo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Anexo findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        Anexo anexo = anexoRepository.findOne(id);
        return anexo;
    }

    /**
     *  Delete the  anexo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.delete(id);
    }
}
