package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Anexo_prevencion;
import com.oplever.sioe.service.Anexo_prevencionService;
import com.oplever.sioe.web.rest.util.HeaderUtil;
import com.oplever.sioe.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Anexo_prevencion.
 */
@RestController
@RequestMapping("/api")
public class Anexo_prevencionResource {

    private final Logger log = LoggerFactory.getLogger(Anexo_prevencionResource.class);

    private static final String ENTITY_NAME = "anexo_prevencion";
        
    private final Anexo_prevencionService anexo_prevencionService;

    public Anexo_prevencionResource(Anexo_prevencionService anexo_prevencionService) {
        this.anexo_prevencionService = anexo_prevencionService;
    }

    /**
     * POST  /anexo-prevencions : Create a new anexo_prevencion.
     *
     * @param anexo_prevencion the anexo_prevencion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anexo_prevencion, or with status 400 (Bad Request) if the anexo_prevencion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anexo-prevencions")
    @Timed
    public ResponseEntity<Anexo_prevencion> createAnexo_prevencion(@Valid @RequestBody Anexo_prevencion anexo_prevencion) throws URISyntaxException {
        log.debug("REST request to save Anexo_prevencion : {}", anexo_prevencion);
        if (anexo_prevencion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new anexo_prevencion cannot already have an ID")).body(null);
        }
        Anexo_prevencion result = anexo_prevencionService.save(anexo_prevencion);
        return ResponseEntity.created(new URI("/api/anexo-prevencions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anexo-prevencions : Updates an existing anexo_prevencion.
     *
     * @param anexo_prevencion the anexo_prevencion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anexo_prevencion,
     * or with status 400 (Bad Request) if the anexo_prevencion is not valid,
     * or with status 500 (Internal Server Error) if the anexo_prevencion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anexo-prevencions")
    @Timed
    public ResponseEntity<Anexo_prevencion> updateAnexo_prevencion(@Valid @RequestBody Anexo_prevencion anexo_prevencion) throws URISyntaxException {
        log.debug("REST request to update Anexo_prevencion : {}", anexo_prevencion);
        if (anexo_prevencion.getId() == null) {
            return createAnexo_prevencion(anexo_prevencion);
        }
        Anexo_prevencion result = anexo_prevencionService.save(anexo_prevencion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anexo_prevencion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anexo-prevencions : get all the anexo_prevencions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of anexo_prevencions in body
     */
    @GetMapping("/anexo-prevencions")
    @Timed
    public ResponseEntity<List<Anexo_prevencion>> getAllAnexo_prevencions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Anexo_prevencions");
        Page<Anexo_prevencion> page = anexo_prevencionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/anexo-prevencions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /anexo-prevencions/:id : get the "id" anexo_prevencion.
     *
     * @param id the id of the anexo_prevencion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anexo_prevencion, or with status 404 (Not Found)
     */
    @GetMapping("/anexo-prevencions/{id}")
    @Timed
    public ResponseEntity<Anexo_prevencion> getAnexo_prevencion(@PathVariable Long id) {
        log.debug("REST request to get Anexo_prevencion : {}", id);
        Anexo_prevencion anexo_prevencion = anexo_prevencionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anexo_prevencion));
    }

    /**
     * DELETE  /anexo-prevencions/:id : delete the "id" anexo_prevencion.
     *
     * @param id the id of the anexo_prevencion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anexo-prevencions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnexo_prevencion(@PathVariable Long id) {
        log.debug("REST request to delete Anexo_prevencion : {}", id);
        anexo_prevencionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
