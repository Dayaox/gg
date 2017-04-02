package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Procedente;
import com.oplever.sioe.repository.ProcedenteRepository;
import com.oplever.sioe.service.ProcedenteService;
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
 * Test class for the ProcedenteResource REST controller.
 *
 * @see ProcedenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class ProcedenteResourceIntTest {

    private static final String DEFAULT_ACTA = "AAAAAAAAAA";
    private static final String UPDATED_ACTA = "BBBBBBBBBB";

    private static final String DEFAULT_ACUERDO = "AAAAAAAAAA";
    private static final String UPDATED_ACUERDO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_COMPLETADO = false;
    private static final Boolean UPDATED_STATUS_COMPLETADO = true;

    @Autowired
    private ProcedenteRepository procedenteRepository;

    @Autowired
    private ProcedenteService procedenteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcedenteMockMvc;

    private Procedente procedente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcedenteResource procedenteResource = new ProcedenteResource(procedenteService);
        this.restProcedenteMockMvc = MockMvcBuilders.standaloneSetup(procedenteResource)
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
    public static Procedente createEntity(EntityManager em) {
        Procedente procedente = new Procedente()
            .acta(DEFAULT_ACTA)
            .acuerdo(DEFAULT_ACUERDO)
            .notificacion(DEFAULT_NOTIFICACION)
            .status_completado(DEFAULT_STATUS_COMPLETADO);
        return procedente;
    }

    @Before
    public void initTest() {
        procedente = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedente() throws Exception {
        int databaseSizeBeforeCreate = procedenteRepository.findAll().size();

        // Create the Procedente
        restProcedenteMockMvc.perform(post("/api/procedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedente)))
            .andExpect(status().isCreated());

        // Validate the Procedente in the database
        List<Procedente> procedenteList = procedenteRepository.findAll();
        assertThat(procedenteList).hasSize(databaseSizeBeforeCreate + 1);
        Procedente testProcedente = procedenteList.get(procedenteList.size() - 1);
        assertThat(testProcedente.getActa()).isEqualTo(DEFAULT_ACTA);
        assertThat(testProcedente.getAcuerdo()).isEqualTo(DEFAULT_ACUERDO);
        assertThat(testProcedente.getNotificacion()).isEqualTo(DEFAULT_NOTIFICACION);
        assertThat(testProcedente.isStatus_completado()).isEqualTo(DEFAULT_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void createProcedenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedenteRepository.findAll().size();

        // Create the Procedente with an existing ID
        procedente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedenteMockMvc.perform(post("/api/procedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedente)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Procedente> procedenteList = procedenteRepository.findAll();
        assertThat(procedenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcedentes() throws Exception {
        // Initialize the database
        procedenteRepository.saveAndFlush(procedente);

        // Get all the procedenteList
        restProcedenteMockMvc.perform(get("/api/procedentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedente.getId().intValue())))
            .andExpect(jsonPath("$.[*].acta").value(hasItem(DEFAULT_ACTA.toString())))
            .andExpect(jsonPath("$.[*].acuerdo").value(hasItem(DEFAULT_ACUERDO.toString())))
            .andExpect(jsonPath("$.[*].notificacion").value(hasItem(DEFAULT_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].status_completado").value(hasItem(DEFAULT_STATUS_COMPLETADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getProcedente() throws Exception {
        // Initialize the database
        procedenteRepository.saveAndFlush(procedente);

        // Get the procedente
        restProcedenteMockMvc.perform(get("/api/procedentes/{id}", procedente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedente.getId().intValue()))
            .andExpect(jsonPath("$.acta").value(DEFAULT_ACTA.toString()))
            .andExpect(jsonPath("$.acuerdo").value(DEFAULT_ACUERDO.toString()))
            .andExpect(jsonPath("$.notificacion").value(DEFAULT_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.status_completado").value(DEFAULT_STATUS_COMPLETADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedente() throws Exception {
        // Get the procedente
        restProcedenteMockMvc.perform(get("/api/procedentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedente() throws Exception {
        // Initialize the database
        procedenteService.save(procedente);

        int databaseSizeBeforeUpdate = procedenteRepository.findAll().size();

        // Update the procedente
        Procedente updatedProcedente = procedenteRepository.findOne(procedente.getId());
        updatedProcedente
            .acta(UPDATED_ACTA)
            .acuerdo(UPDATED_ACUERDO)
            .notificacion(UPDATED_NOTIFICACION)
            .status_completado(UPDATED_STATUS_COMPLETADO);

        restProcedenteMockMvc.perform(put("/api/procedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcedente)))
            .andExpect(status().isOk());

        // Validate the Procedente in the database
        List<Procedente> procedenteList = procedenteRepository.findAll();
        assertThat(procedenteList).hasSize(databaseSizeBeforeUpdate);
        Procedente testProcedente = procedenteList.get(procedenteList.size() - 1);
        assertThat(testProcedente.getActa()).isEqualTo(UPDATED_ACTA);
        assertThat(testProcedente.getAcuerdo()).isEqualTo(UPDATED_ACUERDO);
        assertThat(testProcedente.getNotificacion()).isEqualTo(UPDATED_NOTIFICACION);
        assertThat(testProcedente.isStatus_completado()).isEqualTo(UPDATED_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedente() throws Exception {
        int databaseSizeBeforeUpdate = procedenteRepository.findAll().size();

        // Create the Procedente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcedenteMockMvc.perform(put("/api/procedentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedente)))
            .andExpect(status().isCreated());

        // Validate the Procedente in the database
        List<Procedente> procedenteList = procedenteRepository.findAll();
        assertThat(procedenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcedente() throws Exception {
        // Initialize the database
        procedenteService.save(procedente);

        int databaseSizeBeforeDelete = procedenteRepository.findAll().size();

        // Get the procedente
        restProcedenteMockMvc.perform(delete("/api/procedentes/{id}", procedente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedente> procedenteList = procedenteRepository.findAll();
        assertThat(procedenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedente.class);
    }
}
