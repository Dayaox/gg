package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.UsuarioService;
import com.oplever.sioe.domain.Usuario;
import com.oplever.sioe.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Usuario.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

    private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Save a usuario.
     *
     * @param usuario the entity to save
     * @return the persisted entity
     */
    @Override
    public Usuario save(Usuario usuario) {
        log.debug("Request to save Usuario : {}", usuario);
        Usuario result = usuarioRepository.save(usuario);
        return result;
    }

    /**
     *  Get all the usuarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        Page<Usuario> result = usuarioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one usuario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Usuario findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        Usuario usuario = usuarioRepository.findOne(id);
        return usuario;
    }

    /**
     *  Delete the  usuario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.delete(id);
    }
}
