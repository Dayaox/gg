package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Prevencion;
import com.oplever.sioe.repository.PrevencionRepository;
import com.oplever.sioe.service.PrevencionService;
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
 * Test class for the PrevencionResource REST controller.
 *
 * @see PrevencionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class PrevencionResourceIntTest {

    private static final String DEFAULT_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPUESTA = "AAAAAAAAAA";
    private static final String UPDATED_RESPUESTA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private PrevencionRepository prevencionRepository;

    @Autowired
    private PrevencionService prevencionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrevencionMockMvc;

    private Prevencion prevencion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrevencionResource prevencionResource = new PrevencionResource(prevencionService);
        this.restPrevencionMockMvc = MockMvcBuilders.standaloneSetup(prevencionResource)
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
    public static Prevencion createEntity(EntityManager em) {
        Prevencion prevencion = new Prevencion()
            .oficio(DEFAULT_OFICIO)
            .notificacion(DEFAULT_NOTIFICACION)
            .respuesta(DEFAULT_RESPUESTA)
            .status(DEFAULT_STATUS);
        return prevencion;
    }

    @Before
    public void initTest() {
        prevencion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrevencion() throws Exception {
        int databaseSizeBeforeCreate = prevencionRepository.findAll().size();

        // Create the Prevencion
        restPrevencionMockMvc.perform(post("/api/prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prevencion)))
            .andExpect(status().isCreated());

        // Validate the Prevencion in the database
        List<Prevencion> prevencionList = prevencionRepository.findAll();
        assertThat(prevencionList).hasSize(databaseSizeBeforeCreate + 1);
        Prevencion testPrevencion = prevencionList.get(prevencionList.size() - 1);
        assertThat(testPrevencion.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testPrevencion.getNotificacion()).isEqualTo(DEFAULT_NOTIFICACION);
        assertThat(testPrevencion.getRespuesta()).isEqualTo(DEFAULT_RESPUESTA);
        assertThat(testPrevencion.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPrevencionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prevencionRepository.findAll().size();

        // Create the Prevencion with an existing ID
        prevencion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrevencionMockMvc.perform(post("/api/prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prevencion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Prevencion> prevencionList = prevencionRepository.findAll();
        assertThat(prevencionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrevencions() throws Exception {
        // Initialize the database
        prevencionRepository.saveAndFlush(prevencion);

        // Get all the prevencionList
        restPrevencionMockMvc.perform(get("/api/prevencions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prevencion.getId().intValue())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO.toString())))
            .andExpect(jsonPath("$.[*].notificacion").value(hasItem(DEFAULT_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].respuesta").value(hasItem(DEFAULT_RESPUESTA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrevencion() throws Exception {
        // Initialize the database
        prevencionRepository.saveAndFlush(prevencion);

        // Get the prevencion
        restPrevencionMockMvc.perform(get("/api/prevencions/{id}", prevencion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prevencion.getId().intValue()))
            .andExpect(jsonPath("$.oficio").value(DEFAULT_OFICIO.toString()))
            .andExpect(jsonPath("$.notificacion").value(DEFAULT_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.respuesta").value(DEFAULT_RESPUESTA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrevencion() throws Exception {
        // Get the prevencion
        restPrevencionMockMvc.perform(get("/api/prevencions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrevencion() throws Exception {
        // Initialize the database
        prevencionService.save(prevencion);

        int databaseSizeBeforeUpdate = prevencionRepository.findAll().size();

        // Update the prevencion
        Prevencion updatedPrevencion = prevencionRepository.findOne(prevencion.getId());
        updatedPrevencion
            .oficio(UPDATED_OFICIO)
            .notificacion(UPDATED_NOTIFICACION)
            .respuesta(UPDATED_RESPUESTA)
            .status(UPDATED_STATUS);

        restPrevencionMockMvc.perform(put("/api/prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrevencion)))
            .andExpect(status().isOk());

        // Validate the Prevencion in the database
        List<Prevencion> prevencionList = prevencionRepository.findAll();
        assertThat(prevencionList).hasSize(databaseSizeBeforeUpdate);
        Prevencion testPrevencion = prevencionList.get(prevencionList.size() - 1);
        assertThat(testPrevencion.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testPrevencion.getNotificacion()).isEqualTo(UPDATED_NOTIFICACION);
        assertThat(testPrevencion.getRespuesta()).isEqualTo(UPDATED_RESPUESTA);
        assertThat(testPrevencion.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrevencion() throws Exception {
        int databaseSizeBeforeUpdate = prevencionRepository.findAll().size();

        // Create the Prevencion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrevencionMockMvc.perform(put("/api/prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prevencion)))
            .andExpect(status().isCreated());

        // Validate the Prevencion in the database
        List<Prevencion> prevencionList = prevencionRepository.findAll();
        assertThat(prevencionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrevencion() throws Exception {
        // Initialize the database
        prevencionService.save(prevencion);

        int databaseSizeBeforeDelete = prevencionRepository.findAll().size();

        // Get the prevencion
        restPrevencionMockMvc.perform(delete("/api/prevencions/{id}", prevencion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prevencion> prevencionList = prevencionRepository.findAll();
        assertThat(prevencionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prevencion.class);
    }
}
