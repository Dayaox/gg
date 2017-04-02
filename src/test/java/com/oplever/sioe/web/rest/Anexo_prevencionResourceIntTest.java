package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Anexo_prevencion;
import com.oplever.sioe.repository.Anexo_prevencionRepository;
import com.oplever.sioe.service.Anexo_prevencionService;
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
 * Test class for the Anexo_prevencionResource REST controller.
 *
 * @see Anexo_prevencionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class Anexo_prevencionResourceIntTest {

    private static final String DEFAULT_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_SOLICITUD = "AAAAAAAAAA";
    private static final String UPDATED_ID_SOLICITUD = "BBBBBBBBBB";

    @Autowired
    private Anexo_prevencionRepository anexo_prevencionRepository;

    @Autowired
    private Anexo_prevencionService anexo_prevencionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnexo_prevencionMockMvc;

    private Anexo_prevencion anexo_prevencion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Anexo_prevencionResource anexo_prevencionResource = new Anexo_prevencionResource(anexo_prevencionService);
        this.restAnexo_prevencionMockMvc = MockMvcBuilders.standaloneSetup(anexo_prevencionResource)
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
    public static Anexo_prevencion createEntity(EntityManager em) {
        Anexo_prevencion anexo_prevencion = new Anexo_prevencion()
            .archivo(DEFAULT_ARCHIVO)
            .descripcion(DEFAULT_DESCRIPCION)
            .id_solicitud(DEFAULT_ID_SOLICITUD);
        return anexo_prevencion;
    }

    @Before
    public void initTest() {
        anexo_prevencion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo_prevencion() throws Exception {
        int databaseSizeBeforeCreate = anexo_prevencionRepository.findAll().size();

        // Create the Anexo_prevencion
        restAnexo_prevencionMockMvc.perform(post("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_prevencion)))
            .andExpect(status().isCreated());

        // Validate the Anexo_prevencion in the database
        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo_prevencion testAnexo_prevencion = anexo_prevencionList.get(anexo_prevencionList.size() - 1);
        assertThat(testAnexo_prevencion.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testAnexo_prevencion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAnexo_prevencion.getId_solicitud()).isEqualTo(DEFAULT_ID_SOLICITUD);
    }

    @Test
    @Transactional
    public void createAnexo_prevencionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexo_prevencionRepository.findAll().size();

        // Create the Anexo_prevencion with an existing ID
        anexo_prevencion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexo_prevencionMockMvc.perform(post("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_prevencion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkArchivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexo_prevencionRepository.findAll().size();
        // set the field null
        anexo_prevencion.setArchivo(null);

        // Create the Anexo_prevencion, which fails.

        restAnexo_prevencionMockMvc.perform(post("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_prevencion)))
            .andExpect(status().isBadRequest());

        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexo_prevencionRepository.findAll().size();
        // set the field null
        anexo_prevencion.setDescripcion(null);

        // Create the Anexo_prevencion, which fails.

        restAnexo_prevencionMockMvc.perform(post("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_prevencion)))
            .andExpect(status().isBadRequest());

        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnexo_prevencions() throws Exception {
        // Initialize the database
        anexo_prevencionRepository.saveAndFlush(anexo_prevencion);

        // Get all the anexo_prevencionList
        restAnexo_prevencionMockMvc.perform(get("/api/anexo-prevencions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo_prevencion.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(DEFAULT_ARCHIVO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].id_solicitud").value(hasItem(DEFAULT_ID_SOLICITUD.toString())));
    }

    @Test
    @Transactional
    public void getAnexo_prevencion() throws Exception {
        // Initialize the database
        anexo_prevencionRepository.saveAndFlush(anexo_prevencion);

        // Get the anexo_prevencion
        restAnexo_prevencionMockMvc.perform(get("/api/anexo-prevencions/{id}", anexo_prevencion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexo_prevencion.getId().intValue()))
            .andExpect(jsonPath("$.archivo").value(DEFAULT_ARCHIVO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.id_solicitud").value(DEFAULT_ID_SOLICITUD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnexo_prevencion() throws Exception {
        // Get the anexo_prevencion
        restAnexo_prevencionMockMvc.perform(get("/api/anexo-prevencions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo_prevencion() throws Exception {
        // Initialize the database
        anexo_prevencionService.save(anexo_prevencion);

        int databaseSizeBeforeUpdate = anexo_prevencionRepository.findAll().size();

        // Update the anexo_prevencion
        Anexo_prevencion updatedAnexo_prevencion = anexo_prevencionRepository.findOne(anexo_prevencion.getId());
        updatedAnexo_prevencion
            .archivo(UPDATED_ARCHIVO)
            .descripcion(UPDATED_DESCRIPCION)
            .id_solicitud(UPDATED_ID_SOLICITUD);

        restAnexo_prevencionMockMvc.perform(put("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnexo_prevencion)))
            .andExpect(status().isOk());

        // Validate the Anexo_prevencion in the database
        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeUpdate);
        Anexo_prevencion testAnexo_prevencion = anexo_prevencionList.get(anexo_prevencionList.size() - 1);
        assertThat(testAnexo_prevencion.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testAnexo_prevencion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAnexo_prevencion.getId_solicitud()).isEqualTo(UPDATED_ID_SOLICITUD);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo_prevencion() throws Exception {
        int databaseSizeBeforeUpdate = anexo_prevencionRepository.findAll().size();

        // Create the Anexo_prevencion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnexo_prevencionMockMvc.perform(put("/api/anexo-prevencions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_prevencion)))
            .andExpect(status().isCreated());

        // Validate the Anexo_prevencion in the database
        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnexo_prevencion() throws Exception {
        // Initialize the database
        anexo_prevencionService.save(anexo_prevencion);

        int databaseSizeBeforeDelete = anexo_prevencionRepository.findAll().size();

        // Get the anexo_prevencion
        restAnexo_prevencionMockMvc.perform(delete("/api/anexo-prevencions/{id}", anexo_prevencion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anexo_prevencion> anexo_prevencionList = anexo_prevencionRepository.findAll();
        assertThat(anexo_prevencionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anexo_prevencion.class);
    }
}
