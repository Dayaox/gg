package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Improcedente;
import com.oplever.sioe.service.ImprocedenteService;
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
 * REST controller for managing Improcedente.
 */
@RestController
@RequestMapping("/api")
public class ImprocedenteResource {

    private final Logger log = LoggerFactory.getLogger(ImprocedenteResource.class);

    private static final String ENTITY_NAME = "improcedente";
        
    private final ImprocedenteService improcedenteService;

    public ImprocedenteResource(ImprocedenteService improcedenteService) {
        this.improcedenteService = improcedenteService;
    }

    /**
     * POST  /improcedentes : Create a new improcedente.
     *
     * @param improcedente the improcedente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new improcedente, or with status 400 (Bad Request) if the improcedente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/improcedentes")
    @Timed
    public ResponseEntity<Improcedente> createImprocedente(@RequestBody Improcedente improcedente) throws URISyntaxException {
        log.debug("REST request to save Improcedente : {}", improcedente);
        if (improcedente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new improcedente cannot already have an ID")).body(null);
        }
        Improcedente result = improcedenteService.save(improcedente);
        return ResponseEntity.created(new URI("/api/improcedentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /improcedentes : Updates an existing improcedente.
     *
     * @param improcedente the improcedente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated improcedente,
     * or with status 400 (Bad Request) if the improcedente is not valid,
     * or with status 500 (Internal Server Error) if the improcedente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/improcedentes")
    @Timed
    public ResponseEntity<Improcedente> updateImprocedente(@RequestBody Improcedente improcedente) throws URISyntaxException {
        log.debug("REST request to update Improcedente : {}", improcedente);
        if (improcedente.getId() == null) {
            return createImprocedente(improcedente);
        }
        Improcedente result = improcedenteService.save(improcedente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, improcedente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /improcedentes : get all the improcedentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of improcedentes in body
     */
    @GetMapping("/improcedentes")
    @Timed
    public ResponseEntity<List<Improcedente>> getAllImprocedentes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Improcedentes");
        Page<Improcedente> page = improcedenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/improcedentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /improcedentes/:id : get the "id" improcedente.
     *
     * @param id the id of the improcedente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the improcedente, or with status 404 (Not Found)
     */
    @GetMapping("/improcedentes/{id}")
    @Timed
    public ResponseEntity<Improcedente> getImprocedente(@PathVariable Long id) {
        log.debug("REST request to get Improcedente : {}", id);
        Improcedente improcedente = improcedenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(improcedente));
    }

    /**
     * DELETE  /improcedentes/:id : delete the "id" improcedente.
     *
     * @param id the id of the improcedente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/improcedentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteImprocedente(@PathVariable Long id) {
        log.debug("REST request to delete Improcedente : {}", id);
        improcedenteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
