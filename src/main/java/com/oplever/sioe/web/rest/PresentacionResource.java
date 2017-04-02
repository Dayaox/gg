package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Presentacion;
import com.oplever.sioe.service.PresentacionService;
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
 * REST controller for managing Presentacion.
 */
@RestController
@RequestMapping("/api")
public class PresentacionResource {

    private final Logger log = LoggerFactory.getLogger(PresentacionResource.class);

    private static final String ENTITY_NAME = "presentacion";
        
    private final PresentacionService presentacionService;

    public PresentacionResource(PresentacionService presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * POST  /presentacions : Create a new presentacion.
     *
     * @param presentacion the presentacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new presentacion, or with status 400 (Bad Request) if the presentacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/presentacions")
    @Timed
    public ResponseEntity<Presentacion> createPresentacion(@RequestBody Presentacion presentacion) throws URISyntaxException {
        log.debug("REST request to save Presentacion : {}", presentacion);
        if (presentacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new presentacion cannot already have an ID")).body(null);
        }
        Presentacion result = presentacionService.save(presentacion);
        return ResponseEntity.created(new URI("/api/presentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /presentacions : Updates an existing presentacion.
     *
     * @param presentacion the presentacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated presentacion,
     * or with status 400 (Bad Request) if the presentacion is not valid,
     * or with status 500 (Internal Server Error) if the presentacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/presentacions")
    @Timed
    public ResponseEntity<Presentacion> updatePresentacion(@RequestBody Presentacion presentacion) throws URISyntaxException {
        log.debug("REST request to update Presentacion : {}", presentacion);
        if (presentacion.getId() == null) {
            return createPresentacion(presentacion);
        }
        Presentacion result = presentacionService.save(presentacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, presentacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /presentacions : get all the presentacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of presentacions in body
     */
    @GetMapping("/presentacions")
    @Timed
    public ResponseEntity<List<Presentacion>> getAllPresentacions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Presentacions");
        Page<Presentacion> page = presentacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/presentacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /presentacions/:id : get the "id" presentacion.
     *
     * @param id the id of the presentacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the presentacion, or with status 404 (Not Found)
     */
    @GetMapping("/presentacions/{id}")
    @Timed
    public ResponseEntity<Presentacion> getPresentacion(@PathVariable Long id) {
        log.debug("REST request to get Presentacion : {}", id);
        Presentacion presentacion = presentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(presentacion));
    }

    /**
     * DELETE  /presentacions/:id : delete the "id" presentacion.
     *
     * @param id the id of the presentacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/presentacions/{id}")
    @Timed
    public ResponseEntity<Void> deletePresentacion(@PathVariable Long id) {
        log.debug("REST request to delete Presentacion : {}", id);
        presentacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
