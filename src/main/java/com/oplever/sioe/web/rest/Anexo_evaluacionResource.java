package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Anexo_evaluacion;
import com.oplever.sioe.service.Anexo_evaluacionService;
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
 * REST controller for managing Anexo_evaluacion.
 */
@RestController
@RequestMapping("/api")
public class Anexo_evaluacionResource {

    private final Logger log = LoggerFactory.getLogger(Anexo_evaluacionResource.class);

    private static final String ENTITY_NAME = "anexo_evaluacion";
        
    private final Anexo_evaluacionService anexo_evaluacionService;

    public Anexo_evaluacionResource(Anexo_evaluacionService anexo_evaluacionService) {
        this.anexo_evaluacionService = anexo_evaluacionService;
    }

    /**
     * POST  /anexo-evaluacions : Create a new anexo_evaluacion.
     *
     * @param anexo_evaluacion the anexo_evaluacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anexo_evaluacion, or with status 400 (Bad Request) if the anexo_evaluacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anexo-evaluacions")
    @Timed
    public ResponseEntity<Anexo_evaluacion> createAnexo_evaluacion(@Valid @RequestBody Anexo_evaluacion anexo_evaluacion) throws URISyntaxException {
        log.debug("REST request to save Anexo_evaluacion : {}", anexo_evaluacion);
        if (anexo_evaluacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new anexo_evaluacion cannot already have an ID")).body(null);
        }
        Anexo_evaluacion result = anexo_evaluacionService.save(anexo_evaluacion);
        return ResponseEntity.created(new URI("/api/anexo-evaluacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anexo-evaluacions : Updates an existing anexo_evaluacion.
     *
     * @param anexo_evaluacion the anexo_evaluacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anexo_evaluacion,
     * or with status 400 (Bad Request) if the anexo_evaluacion is not valid,
     * or with status 500 (Internal Server Error) if the anexo_evaluacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anexo-evaluacions")
    @Timed
    public ResponseEntity<Anexo_evaluacion> updateAnexo_evaluacion(@Valid @RequestBody Anexo_evaluacion anexo_evaluacion) throws URISyntaxException {
        log.debug("REST request to update Anexo_evaluacion : {}", anexo_evaluacion);
        if (anexo_evaluacion.getId() == null) {
            return createAnexo_evaluacion(anexo_evaluacion);
        }
        Anexo_evaluacion result = anexo_evaluacionService.save(anexo_evaluacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anexo_evaluacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anexo-evaluacions : get all the anexo_evaluacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of anexo_evaluacions in body
     */
    @GetMapping("/anexo-evaluacions")
    @Timed
    public ResponseEntity<List<Anexo_evaluacion>> getAllAnexo_evaluacions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Anexo_evaluacions");
        Page<Anexo_evaluacion> page = anexo_evaluacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/anexo-evaluacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /anexo-evaluacions/:id : get the "id" anexo_evaluacion.
     *
     * @param id the id of the anexo_evaluacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anexo_evaluacion, or with status 404 (Not Found)
     */
    @GetMapping("/anexo-evaluacions/{id}")
    @Timed
    public ResponseEntity<Anexo_evaluacion> getAnexo_evaluacion(@PathVariable Long id) {
        log.debug("REST request to get Anexo_evaluacion : {}", id);
        Anexo_evaluacion anexo_evaluacion = anexo_evaluacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anexo_evaluacion));
    }

    /**
     * DELETE  /anexo-evaluacions/:id : delete the "id" anexo_evaluacion.
     *
     * @param id the id of the anexo_evaluacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anexo-evaluacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnexo_evaluacion(@PathVariable Long id) {
        log.debug("REST request to delete Anexo_evaluacion : {}", id);
        anexo_evaluacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
