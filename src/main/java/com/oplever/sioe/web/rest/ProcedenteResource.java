package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Procedente;
import com.oplever.sioe.service.ProcedenteService;
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
 * REST controller for managing Procedente.
 */
@RestController
@RequestMapping("/api")
public class ProcedenteResource {

    private final Logger log = LoggerFactory.getLogger(ProcedenteResource.class);

    private static final String ENTITY_NAME = "procedente";
        
    private final ProcedenteService procedenteService;

    public ProcedenteResource(ProcedenteService procedenteService) {
        this.procedenteService = procedenteService;
    }

    /**
     * POST  /procedentes : Create a new procedente.
     *
     * @param procedente the procedente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedente, or with status 400 (Bad Request) if the procedente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedentes")
    @Timed
    public ResponseEntity<Procedente> createProcedente(@RequestBody Procedente procedente) throws URISyntaxException {
        log.debug("REST request to save Procedente : {}", procedente);
        if (procedente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new procedente cannot already have an ID")).body(null);
        }
        Procedente result = procedenteService.save(procedente);
        return ResponseEntity.created(new URI("/api/procedentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedentes : Updates an existing procedente.
     *
     * @param procedente the procedente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedente,
     * or with status 400 (Bad Request) if the procedente is not valid,
     * or with status 500 (Internal Server Error) if the procedente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedentes")
    @Timed
    public ResponseEntity<Procedente> updateProcedente(@RequestBody Procedente procedente) throws URISyntaxException {
        log.debug("REST request to update Procedente : {}", procedente);
        if (procedente.getId() == null) {
            return createProcedente(procedente);
        }
        Procedente result = procedenteService.save(procedente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedentes : get all the procedentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedentes in body
     */
    @GetMapping("/procedentes")
    @Timed
    public ResponseEntity<List<Procedente>> getAllProcedentes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Procedentes");
        Page<Procedente> page = procedenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedentes/:id : get the "id" procedente.
     *
     * @param id the id of the procedente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedente, or with status 404 (Not Found)
     */
    @GetMapping("/procedentes/{id}")
    @Timed
    public ResponseEntity<Procedente> getProcedente(@PathVariable Long id) {
        log.debug("REST request to get Procedente : {}", id);
        Procedente procedente = procedenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procedente));
    }

    /**
     * DELETE  /procedentes/:id : delete the "id" procedente.
     *
     * @param id the id of the procedente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedente(@PathVariable Long id) {
        log.debug("REST request to delete Procedente : {}", id);
        procedenteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
