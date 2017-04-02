package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Evaluacion;
import com.oplever.sioe.service.EvaluacionService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Evaluacion.
 */
@RestController
@RequestMapping("/api")
public class EvaluacionResource {

    private final Logger log = LoggerFactory.getLogger(EvaluacionResource.class);

    private static final String ENTITY_NAME = "evaluacion";
        
    private final EvaluacionService evaluacionService;

    public EvaluacionResource(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    /**
     * POST  /evaluacions : Create a new evaluacion.
     *
     * @param evaluacion the evaluacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluacion, or with status 400 (Bad Request) if the evaluacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluacions")
    @Timed
    public ResponseEntity<Evaluacion> createEvaluacion(@RequestBody Evaluacion evaluacion) throws URISyntaxException {
        log.debug("REST request to save Evaluacion : {}", evaluacion);
        if (evaluacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new evaluacion cannot already have an ID")).body(null);
        }
        Evaluacion result = evaluacionService.save(evaluacion);
        return ResponseEntity.created(new URI("/api/evaluacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluacions : Updates an existing evaluacion.
     *
     * @param evaluacion the evaluacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluacion,
     * or with status 400 (Bad Request) if the evaluacion is not valid,
     * or with status 500 (Internal Server Error) if the evaluacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluacions")
    @Timed
    public ResponseEntity<Evaluacion> updateEvaluacion(@RequestBody Evaluacion evaluacion) throws URISyntaxException {
        log.debug("REST request to update Evaluacion : {}", evaluacion);
        if (evaluacion.getId() == null) {
            return createEvaluacion(evaluacion);
        }
        Evaluacion result = evaluacionService.save(evaluacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluacions : get all the evaluacions.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of evaluacions in body
     */
    @GetMapping("/evaluacions")
    @Timed
    public ResponseEntity<List<Evaluacion>> getAllEvaluacions(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("procedente-is-null".equals(filter)) {
            log.debug("REST request to get all Evaluacions where procedente is null");
            return new ResponseEntity<>(evaluacionService.findAllWhereProcedenteIsNull(),
                    HttpStatus.OK);
        }
        if ("improcedente-is-null".equals(filter)) {
            log.debug("REST request to get all Evaluacions where improcedente is null");
            return new ResponseEntity<>(evaluacionService.findAllWhereImprocedenteIsNull(),
                    HttpStatus.OK);
        }
        if ("presentacion-is-null".equals(filter)) {
            log.debug("REST request to get all Evaluacions where presentacion is null");
            return new ResponseEntity<>(evaluacionService.findAllWherePresentacionIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Evaluacions");
        Page<Evaluacion> page = evaluacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluacions/:id : get the "id" evaluacion.
     *
     * @param id the id of the evaluacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluacion, or with status 404 (Not Found)
     */
    @GetMapping("/evaluacions/{id}")
    @Timed
    public ResponseEntity<Evaluacion> getEvaluacion(@PathVariable Long id) {
        log.debug("REST request to get Evaluacion : {}", id);
        Evaluacion evaluacion = evaluacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evaluacion));
    }

    /**
     * DELETE  /evaluacions/:id : delete the "id" evaluacion.
     *
     * @param id the id of the evaluacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluacion(@PathVariable Long id) {
        log.debug("REST request to delete Evaluacion : {}", id);
        evaluacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
