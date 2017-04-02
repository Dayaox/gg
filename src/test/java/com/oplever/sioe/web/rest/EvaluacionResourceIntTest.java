package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Evaluacion;
import com.oplever.sioe.repository.EvaluacionRepository;
import com.oplever.sioe.service.EvaluacionService;
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
 * Test class for the EvaluacionResource REST controller.
 *
 * @see EvaluacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class EvaluacionResourceIntTest {

    private static final Integer DEFAULT_STATUS_EVALUACION = 1;
    private static final Integer UPDATED_STATUS_EVALUACION = 2;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvaluacionMockMvc;

    private Evaluacion evaluacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluacionResource evaluacionResource = new EvaluacionResource(evaluacionService);
        this.restEvaluacionMockMvc = MockMvcBuilders.standaloneSetup(evaluacionResource)
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
    public static Evaluacion createEntity(EntityManager em) {
        Evaluacion evaluacion = new Evaluacion()
            .status_evaluacion(DEFAULT_STATUS_EVALUACION);
        return evaluacion;
    }

    @Before
    public void initTest() {
        evaluacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluacion() throws Exception {
        int databaseSizeBeforeCreate = evaluacionRepository.findAll().size();

        // Create the Evaluacion
        restEvaluacionMockMvc.perform(post("/api/evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluacion)))
            .andExpect(status().isCreated());

        // Validate the Evaluacion in the database
        List<Evaluacion> evaluacionList = evaluacionRepository.findAll();
        assertThat(evaluacionList).hasSize(databaseSizeBeforeCreate + 1);
        Evaluacion testEvaluacion = evaluacionList.get(evaluacionList.size() - 1);
        assertThat(testEvaluacion.getStatus_evaluacion()).isEqualTo(DEFAULT_STATUS_EVALUACION);
    }

    @Test
    @Transactional
    public void createEvaluacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluacionRepository.findAll().size();

        // Create the Evaluacion with an existing ID
        evaluacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluacionMockMvc.perform(post("/api/evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Evaluacion> evaluacionList = evaluacionRepository.findAll();
        assertThat(evaluacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvaluacions() throws Exception {
        // Initialize the database
        evaluacionRepository.saveAndFlush(evaluacion);

        // Get all the evaluacionList
        restEvaluacionMockMvc.perform(get("/api/evaluacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].status_evaluacion").value(hasItem(DEFAULT_STATUS_EVALUACION)));
    }

    @Test
    @Transactional
    public void getEvaluacion() throws Exception {
        // Initialize the database
        evaluacionRepository.saveAndFlush(evaluacion);

        // Get the evaluacion
        restEvaluacionMockMvc.perform(get("/api/evaluacions/{id}", evaluacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluacion.getId().intValue()))
            .andExpect(jsonPath("$.status_evaluacion").value(DEFAULT_STATUS_EVALUACION));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluacion() throws Exception {
        // Get the evaluacion
        restEvaluacionMockMvc.perform(get("/api/evaluacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluacion() throws Exception {
        // Initialize the database
        evaluacionService.save(evaluacion);

        int databaseSizeBeforeUpdate = evaluacionRepository.findAll().size();

        // Update the evaluacion
        Evaluacion updatedEvaluacion = evaluacionRepository.findOne(evaluacion.getId());
        updatedEvaluacion
            .status_evaluacion(UPDATED_STATUS_EVALUACION);

        restEvaluacionMockMvc.perform(put("/api/evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvaluacion)))
            .andExpect(status().isOk());

        // Validate the Evaluacion in the database
        List<Evaluacion> evaluacionList = evaluacionRepository.findAll();
        assertThat(evaluacionList).hasSize(databaseSizeBeforeUpdate);
        Evaluacion testEvaluacion = evaluacionList.get(evaluacionList.size() - 1);
        assertThat(testEvaluacion.getStatus_evaluacion()).isEqualTo(UPDATED_STATUS_EVALUACION);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluacion() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionRepository.findAll().size();

        // Create the Evaluacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvaluacionMockMvc.perform(put("/api/evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluacion)))
            .andExpect(status().isCreated());

        // Validate the Evaluacion in the database
        List<Evaluacion> evaluacionList = evaluacionRepository.findAll();
        assertThat(evaluacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvaluacion() throws Exception {
        // Initialize the database
        evaluacionService.save(evaluacion);

        int databaseSizeBeforeDelete = evaluacionRepository.findAll().size();

        // Get the evaluacion
        restEvaluacionMockMvc.perform(delete("/api/evaluacions/{id}", evaluacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluacion> evaluacionList = evaluacionRepository.findAll();
        assertThat(evaluacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evaluacion.class);
    }
}
