package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Improcedente;
import com.oplever.sioe.repository.ImprocedenteRepository;
import com.oplever.sioe.service.ImprocedenteService;
import com.oplever.sioe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImprocedenteResource REST controller.
 *
 * @see ImprocedenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class ImprocedenteResourceIntTest {

    private static final String DEFAULT_ACUERDO = "AAAAAAAAAA";
    private static final String UPDATED_ACUERDO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_COMPLETADO = false;
    private static final Boolean UPDATED_STATUS_COMPLETADO = true;

    @Autowired
    private ImprocedenteRepository improcedenteRepository;

    @Autowired
    private ImprocedenteService improcedenteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImprocedenteMockMvc;

    private Improcedente improcedente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImprocedenteResource improcedenteResource = new ImprocedenteResource(improcedenteService);
        this.restImprocedenteMockMvc = MockMvcBuilders.standaloneSetup(improcedenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Improcedente createEntity(EntityManager em) {
        Improcedente improcedente = new Improcedente()
            .acuerdo(DEFAULT_ACUERDO)
            .notificacion(DEFAULT_NOTIFICACION)
            .status_completado(DEFAULT_STATUS_COMPLETADO);
        return improcedente;
    }

    @Before
    public void initTest() {
        improcedente = createEntity(em);
    }

    @Test
    @Transactional
    public void createImprocedente() throws Exception {
        int databaseSizeBeforeCreate = improcedenteRepository.findAll().size();

        // Create the Improcedente
        restImprocedenteMockMvc.perform(post("/api/improcedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(improcedente)))
            .andExpect(status().isCreated());

        // Validate the Improcedente in the database
        List<Improcedente> improcedenteList = improcedenteRepository.findAll();
        assertThat(improcedenteList).hasSize(databaseSizeBeforeCreate + 1);
        Improcedente testImprocedente = improcedenteList.get(improcedenteList.size() - 1);
        assertThat(testImprocedente.getAcuerdo()).isEqualTo(DEFAULT_ACUERDO);
        assertThat(testImprocedente.getNotificacion()).isEqualTo(DEFAULT_NOTIFICACION);
        assertThat(testImprocedente.isStatus_completado()).isEqualTo(DEFAULT_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void createImprocedenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = improcedenteRepository.findAll().size();

        // Create the Improcedente with an existing ID
        improcedente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImprocedenteMockMvc.perform(post("/api/improcedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(improcedente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Improcedente> improcedenteList = improcedenteRepository.findAll();
        assertThat(improcedenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImprocedentes() throws Exception {
        // Initialize the database
        improcedenteRepository.saveAndFlush(improcedente);

        // Get all the improcedenteList
        restImprocedenteMockMvc.perform(get("/api/improcedentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(improcedente.getId().intValue())))
            .andExpect(jsonPath("$.[*].acuerdo").value(hasItem(DEFAULT_ACUERDO.toString())))
            .andExpect(jsonPath("$.[*].notificacion").value(hasItem(DEFAULT_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].status_completado").value(hasItem(DEFAULT_STATUS_COMPLETADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getImprocedente() throws Exception {
        // Initialize the database
        improcedenteRepository.saveAndFlush(improcedente);

        // Get the improcedente
        restImprocedenteMockMvc.perform(get("/api/improcedentes/{id}", improcedente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(improcedente.getId().intValue()))
            .andExpect(jsonPath("$.acuerdo").value(DEFAULT_ACUERDO.toString()))
            .andExpect(jsonPath("$.notificacion").value(DEFAULT_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.status_completado").value(DEFAULT_STATUS_COMPLETADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImprocedente() throws Exception {
        // Get the improcedente
        restImprocedenteMockMvc.perform(get("/api/improcedentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImprocedente() throws Exception {
        // Initialize the database
        improcedenteService.save(improcedente);

        int databaseSizeBeforeUpdate = improcedenteRepository.findAll().size();

        // Update the improcedente
        Improcedente updatedImprocedente = improcedenteRepository.findOne(improcedente.getId());
        updatedImprocedente
            .acuerdo(UPDATED_ACUERDO)
            .notificacion(UPDATED_NOTIFICACION)
            .status_completado(UPDATED_STATUS_COMPLETADO);

        restImprocedenteMockMvc.perform(put("/api/improcedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImprocedente)))
            .andExpect(status().isOk());

        // Validate the Improcedente in the database
        List<Improcedente> improcedenteList = improcedenteRepository.findAll();
        assertThat(improcedenteList).hasSize(databaseSizeBeforeUpdate);
        Improcedente testImprocedente = improcedenteList.get(improcedenteList.size() - 1);
        assertThat(testImprocedente.getAcuerdo()).isEqualTo(UPDATED_ACUERDO);
        assertThat(testImprocedente.getNotificacion()).isEqualTo(UPDATED_NOTIFICACION);
        assertThat(testImprocedente.isStatus_completado()).isEqualTo(UPDATED_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void updateNonExistingImprocedente() throws Exception {
        int databaseSizeBeforeUpdate = improcedenteRepository.findAll().size();

        // Create the Improcedente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImprocedenteMockMvc.perform(put("/api/improcedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(improcedente)))
            .andExpect(status().isCreated());

        // Validate the Improcedente in the database
        List<Improcedente> improcedenteList = improcedenteRepository.findAll();
        assertThat(improcedenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImprocedente() throws Exception {
        // Initialize the database
        improcedenteService.save(improcedente);

        int databaseSizeBeforeDelete = improcedenteRepository.findAll().size();

        // Get the improcedente
        restImprocedenteMockMvc.perform(delete("/api/improcedentes/{id}", improcedente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Improcedente> improcedenteList = improcedenteRepository.findAll();
        assertThat(improcedenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Improcedente.class);
    }
}
