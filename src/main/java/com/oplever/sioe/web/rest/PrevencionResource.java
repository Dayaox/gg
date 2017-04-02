package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Prevencion;
import com.oplever.sioe.service.PrevencionService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prevencion.
 */
@RestController
@RequestMapping("/api")
public class PrevencionResource {

    private final Logger log = LoggerFactory.getLogger(PrevencionResource.class);

    private static final String ENTITY_NAME = "prevencion";
        
    private final PrevencionService prevencionService;

    public PrevencionResource(PrevencionService prevencionService) {
        this.prevencionService = prevencionService;
    }

    /**
     * POST  /prevencions : Create a new prevencion.
     *
     * @param prevencion the prevencion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prevencion, or with status 400 (Bad Request) if the prevencion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prevencions")
    @Timed
    public ResponseEntity<Prevencion> createPrevencion(@RequestBody Prevencion prevencion) throws URISyntaxException {
        log.debug("REST request to save Prevencion : {}", prevencion);
        if (prevencion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prevencion cannot already have an ID")).body(null);
        }
        Prevencion result = prevencionService.save(prevencion);
        return ResponseEntity.created(new URI("/api/prevencions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prevencions : Updates an existing prevencion.
     *
     * @param prevencion the prevencion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prevencion,
     * or with status 400 (Bad Request) if the prevencion is not valid,
     * or with status 500 (Internal Server Error) if the prevencion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prevencions")
    @Timed
    public ResponseEntity<Prevencion> updatePrevencion(@RequestBody Prevencion prevencion) throws URISyntaxException {
        log.debug("REST request to update Prevencion : {}", prevencion);
        if (prevencion.getId() == null) {
            return createPrevencion(prevencion);
        }
        Prevencion result = prevencionService.save(prevencion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prevencion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prevencions : get all the prevencions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prevencions in body
     */
    @GetMapping("/prevencions")
    @Timed
    public ResponseEntity<List<Prevencion>> getAllPrevencions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Prevencions");
        Page<Prevencion> page = prevencionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prevencions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prevencions/:id : get the "id" prevencion.
     *
     * @param id the id of the prevencion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prevencion, or with status 404 (Not Found)
     */
    @GetMapping("/prevencions/{id}")
    @Timed
    public ResponseEntity<Prevencion> getPrevencion(@PathVariable Long id) {
        log.debug("REST request to get Prevencion : {}", id);
        Prevencion prevencion = prevencionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prevencion));
    }

    /**
     * DELETE  /prevencions/:id : delete the "id" prevencion.
     *
     * @param id the id of the prevencion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prevencions/{id}")
    @Timed
    public ResponseEntity<Void> deletePrevencion(@PathVariable Long id) {
        log.debug("REST request to delete Prevencion : {}", id);
        prevencionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
