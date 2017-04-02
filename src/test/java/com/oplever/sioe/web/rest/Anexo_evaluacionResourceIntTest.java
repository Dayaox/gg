package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Anexo_evaluacion;
import com.oplever.sioe.repository.Anexo_evaluacionRepository;
import com.oplever.sioe.service.Anexo_evaluacionService;
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
 * Test class for the Anexo_evaluacionResource REST controller.
 *
 * @see Anexo_evaluacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class Anexo_evaluacionResourceIntTest {

    private static final String DEFAULT_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PETICION = "AAAAAAAAAA";
    private static final String UPDATED_ID_PETICION = "BBBBBBBBBB";

    @Autowired
    private Anexo_evaluacionRepository anexo_evaluacionRepository;

    @Autowired
    private Anexo_evaluacionService anexo_evaluacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnexo_evaluacionMockMvc;

    private Anexo_evaluacion anexo_evaluacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Anexo_evaluacionResource anexo_evaluacionResource = new Anexo_evaluacionResource(anexo_evaluacionService);
        this.restAnexo_evaluacionMockMvc = MockMvcBuilders.standaloneSetup(anexo_evaluacionResource)
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
    public static Anexo_evaluacion createEntity(EntityManager em) {
        Anexo_evaluacion anexo_evaluacion = new Anexo_evaluacion()
            .archivo(DEFAULT_ARCHIVO)
            .descripcion(DEFAULT_DESCRIPCION)
            .id_peticion(DEFAULT_ID_PETICION);
        return anexo_evaluacion;
    }

    @Before
    public void initTest() {
        anexo_evaluacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnexo_evaluacion() throws Exception {
        int databaseSizeBeforeCreate = anexo_evaluacionRepository.findAll().size();

        // Create the Anexo_evaluacion
        restAnexo_evaluacionMockMvc.perform(post("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_evaluacion)))
            .andExpect(status().isCreated());

        // Validate the Anexo_evaluacion in the database
        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeCreate + 1);
        Anexo_evaluacion testAnexo_evaluacion = anexo_evaluacionList.get(anexo_evaluacionList.size() - 1);
        assertThat(testAnexo_evaluacion.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testAnexo_evaluacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAnexo_evaluacion.getId_peticion()).isEqualTo(DEFAULT_ID_PETICION);
    }

    @Test
    @Transactional
    public void createAnexo_evaluacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anexo_evaluacionRepository.findAll().size();

        // Create the Anexo_evaluacion with an existing ID
        anexo_evaluacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexo_evaluacionMockMvc.perform(post("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_evaluacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkArchivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexo_evaluacionRepository.findAll().size();
        // set the field null
        anexo_evaluacion.setArchivo(null);

        // Create the Anexo_evaluacion, which fails.

        restAnexo_evaluacionMockMvc.perform(post("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_evaluacion)))
            .andExpect(status().isBadRequest());

        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = anexo_evaluacionRepository.findAll().size();
        // set the field null
        anexo_evaluacion.setDescripcion(null);

        // Create the Anexo_evaluacion, which fails.

        restAnexo_evaluacionMockMvc.perform(post("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_evaluacion)))
            .andExpect(status().isBadRequest());

        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnexo_evaluacions() throws Exception {
        // Initialize the database
        anexo_evaluacionRepository.saveAndFlush(anexo_evaluacion);

        // Get all the anexo_evaluacionList
        restAnexo_evaluacionMockMvc.perform(get("/api/anexo-evaluacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexo_evaluacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(DEFAULT_ARCHIVO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].id_peticion").value(hasItem(DEFAULT_ID_PETICION.toString())));
    }

    @Test
    @Transactional
    public void getAnexo_evaluacion() throws Exception {
        // Initialize the database
        anexo_evaluacionRepository.saveAndFlush(anexo_evaluacion);

        // Get the anexo_evaluacion
        restAnexo_evaluacionMockMvc.perform(get("/api/anexo-evaluacions/{id}", anexo_evaluacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anexo_evaluacion.getId().intValue()))
            .andExpect(jsonPath("$.archivo").value(DEFAULT_ARCHIVO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.id_peticion").value(DEFAULT_ID_PETICION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnexo_evaluacion() throws Exception {
        // Get the anexo_evaluacion
        restAnexo_evaluacionMockMvc.perform(get("/api/anexo-evaluacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnexo_evaluacion() throws Exception {
        // Initialize the database
        anexo_evaluacionService.save(anexo_evaluacion);

        int databaseSizeBeforeUpdate = anexo_evaluacionRepository.findAll().size();

        // Update the anexo_evaluacion
        Anexo_evaluacion updatedAnexo_evaluacion = anexo_evaluacionRepository.findOne(anexo_evaluacion.getId());
        updatedAnexo_evaluacion
            .archivo(UPDATED_ARCHIVO)
            .descripcion(UPDATED_DESCRIPCION)
            .id_peticion(UPDATED_ID_PETICION);

        restAnexo_evaluacionMockMvc.perform(put("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnexo_evaluacion)))
            .andExpect(status().isOk());

        // Validate the Anexo_evaluacion in the database
        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeUpdate);
        Anexo_evaluacion testAnexo_evaluacion = anexo_evaluacionList.get(anexo_evaluacionList.size() - 1);
        assertThat(testAnexo_evaluacion.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testAnexo_evaluacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAnexo_evaluacion.getId_peticion()).isEqualTo(UPDATED_ID_PETICION);
    }

    @Test
    @Transactional
    public void updateNonExistingAnexo_evaluacion() throws Exception {
        int databaseSizeBeforeUpdate = anexo_evaluacionRepository.findAll().size();

        // Create the Anexo_evaluacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnexo_evaluacionMockMvc.perform(put("/api/anexo-evaluacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anexo_evaluacion)))
            .andExpect(status().isCreated());

        // Validate the Anexo_evaluacion in the database
        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnexo_evaluacion() throws Exception {
        // Initialize the database
        anexo_evaluacionService.save(anexo_evaluacion);

        int databaseSizeBeforeDelete = anexo_evaluacionRepository.findAll().size();

        // Get the anexo_evaluacion
        restAnexo_evaluacionMockMvc.perform(delete("/api/anexo-evaluacions/{id}", anexo_evaluacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anexo_evaluacion> anexo_evaluacionList = anexo_evaluacionRepository.findAll();
        assertThat(anexo_evaluacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anexo_evaluacion.class);
    }
}
