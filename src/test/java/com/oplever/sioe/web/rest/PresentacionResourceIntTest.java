package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Presentacion;
import com.oplever.sioe.repository.PresentacionRepository;
import com.oplever.sioe.service.PresentacionService;
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
 * Test class for the PresentacionResource REST controller.
 *
 * @see PresentacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class PresentacionResourceIntTest {

    private static final String DEFAULT_ACUERDO = "AAAAAAAAAA";
    private static final String UPDATED_ACUERDO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS_COMPLETADO = false;
    private static final Boolean UPDATED_STATUS_COMPLETADO = true;

    @Autowired
    private PresentacionRepository presentacionRepository;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPresentacionMockMvc;

    private Presentacion presentacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PresentacionResource presentacionResource = new PresentacionResource(presentacionService);
        this.restPresentacionMockMvc = MockMvcBuilders.standaloneSetup(presentacionResource)
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
    public static Presentacion createEntity(EntityManager em) {
        Presentacion presentacion = new Presentacion()
            .acuerdo(DEFAULT_ACUERDO)
            .notificacion(DEFAULT_NOTIFICACION)
            .status_completado(DEFAULT_STATUS_COMPLETADO);
        return presentacion;
    }

    @Before
    public void initTest() {
        presentacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresentacion() throws Exception {
        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();

        // Create the Presentacion
        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
            .andExpect(status().isCreated());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate + 1);
        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
        assertThat(testPresentacion.getAcuerdo()).isEqualTo(DEFAULT_ACUERDO);
        assertThat(testPresentacion.getNotificacion()).isEqualTo(DEFAULT_NOTIFICACION);
        assertThat(testPresentacion.isStatus_completado()).isEqualTo(DEFAULT_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void createPresentacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();

        // Create the Presentacion with an existing ID
        presentacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPresentacions() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        // Get all the presentacionList
        restPresentacionMockMvc.perform(get("/api/presentacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presentacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].acuerdo").value(hasItem(DEFAULT_ACUERDO.toString())))
            .andExpect(jsonPath("$.[*].notificacion").value(hasItem(DEFAULT_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].status_completado").value(hasItem(DEFAULT_STATUS_COMPLETADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPresentacion() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", presentacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presentacion.getId().intValue()))
            .andExpect(jsonPath("$.acuerdo").value(DEFAULT_ACUERDO.toString()))
            .andExpect(jsonPath("$.notificacion").value(DEFAULT_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.status_completado").value(DEFAULT_STATUS_COMPLETADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPresentacion() throws Exception {
        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresentacion() throws Exception {
        // Initialize the database
        presentacionService.save(presentacion);

        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();

        // Update the presentacion
        Presentacion updatedPresentacion = presentacionRepository.findOne(presentacion.getId());
        updatedPresentacion
            .acuerdo(UPDATED_ACUERDO)
            .notificacion(UPDATED_NOTIFICACION)
            .status_completado(UPDATED_STATUS_COMPLETADO);

        restPresentacionMockMvc.perform(put("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPresentacion)))
            .andExpect(status().isOk());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate);
        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
        assertThat(testPresentacion.getAcuerdo()).isEqualTo(UPDATED_ACUERDO);
        assertThat(testPresentacion.getNotificacion()).isEqualTo(UPDATED_NOTIFICACION);
        assertThat(testPresentacion.isStatus_completado()).isEqualTo(UPDATED_STATUS_COMPLETADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPresentacion() throws Exception {
        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();

        // Create the Presentacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPresentacionMockMvc.perform(put("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
            .andExpect(status().isCreated());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePresentacion() throws Exception {
        // Initialize the database
        presentacionService.save(presentacion);

        int databaseSizeBeforeDelete = presentacionRepository.findAll().size();

        // Get the presentacion
        restPresentacionMockMvc.perform(delete("/api/presentacions/{id}", presentacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presentacion.class);
    }
}
