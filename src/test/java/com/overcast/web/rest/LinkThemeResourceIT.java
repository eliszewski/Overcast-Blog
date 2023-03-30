package com.overcast.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.overcast.IntegrationTest;
import com.overcast.domain.LinkTheme;
import com.overcast.domain.enumeration.Theme;
import com.overcast.repository.LinkThemeRepository;
import com.overcast.service.LinkThemeService;
import com.overcast.service.dto.LinkThemeDTO;
import com.overcast.service.mapper.LinkThemeMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LinkThemeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LinkThemeResourceIT {

    private static final Boolean DEFAULT_IS_CUSTOM = false;
    private static final Boolean UPDATED_IS_CUSTOM = true;

    private static final String DEFAULT_CUSTOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_NAME = "BBBBBBBBBB";

    private static final Theme DEFAULT_PRESET_THEME = Theme.MUSICOFTHEDAY;
    private static final Theme UPDATED_PRESET_THEME = Theme.ANIMAL;

    private static final String ENTITY_API_URL = "/api/link-themes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkThemeRepository linkThemeRepository;

    @Mock
    private LinkThemeRepository linkThemeRepositoryMock;

    @Autowired
    private LinkThemeMapper linkThemeMapper;

    @Mock
    private LinkThemeService linkThemeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkThemeMockMvc;

    private LinkTheme linkTheme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkTheme createEntity(EntityManager em) {
        LinkTheme linkTheme = new LinkTheme().isCustom(DEFAULT_IS_CUSTOM).customName(DEFAULT_CUSTOM_NAME).presetTheme(DEFAULT_PRESET_THEME);
        return linkTheme;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinkTheme createUpdatedEntity(EntityManager em) {
        LinkTheme linkTheme = new LinkTheme().isCustom(UPDATED_IS_CUSTOM).customName(UPDATED_CUSTOM_NAME).presetTheme(UPDATED_PRESET_THEME);
        return linkTheme;
    }

    @BeforeEach
    public void initTest() {
        linkTheme = createEntity(em);
    }

    @Test
    @Transactional
    void createLinkTheme() throws Exception {
        int databaseSizeBeforeCreate = linkThemeRepository.findAll().size();
        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);
        restLinkThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkThemeDTO)))
            .andExpect(status().isCreated());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeCreate + 1);
        LinkTheme testLinkTheme = linkThemeList.get(linkThemeList.size() - 1);
        assertThat(testLinkTheme.getIsCustom()).isEqualTo(DEFAULT_IS_CUSTOM);
        assertThat(testLinkTheme.getCustomName()).isEqualTo(DEFAULT_CUSTOM_NAME);
        assertThat(testLinkTheme.getPresetTheme()).isEqualTo(DEFAULT_PRESET_THEME);
    }

    @Test
    @Transactional
    void createLinkThemeWithExistingId() throws Exception {
        // Create the LinkTheme with an existing ID
        linkTheme.setId(1L);
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        int databaseSizeBeforeCreate = linkThemeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkThemeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsCustomIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkThemeRepository.findAll().size();
        // set the field null
        linkTheme.setIsCustom(null);

        // Create the LinkTheme, which fails.
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        restLinkThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkThemeDTO)))
            .andExpect(status().isBadRequest());

        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLinkThemes() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        // Get all the linkThemeList
        restLinkThemeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linkTheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCustom").value(hasItem(DEFAULT_IS_CUSTOM.booleanValue())))
            .andExpect(jsonPath("$.[*].customName").value(hasItem(DEFAULT_CUSTOM_NAME)))
            .andExpect(jsonPath("$.[*].presetTheme").value(hasItem(DEFAULT_PRESET_THEME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLinkThemesWithEagerRelationshipsIsEnabled() throws Exception {
        when(linkThemeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLinkThemeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(linkThemeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLinkThemesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(linkThemeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLinkThemeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(linkThemeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLinkTheme() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        // Get the linkTheme
        restLinkThemeMockMvc
            .perform(get(ENTITY_API_URL_ID, linkTheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linkTheme.getId().intValue()))
            .andExpect(jsonPath("$.isCustom").value(DEFAULT_IS_CUSTOM.booleanValue()))
            .andExpect(jsonPath("$.customName").value(DEFAULT_CUSTOM_NAME))
            .andExpect(jsonPath("$.presetTheme").value(DEFAULT_PRESET_THEME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLinkTheme() throws Exception {
        // Get the linkTheme
        restLinkThemeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLinkTheme() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();

        // Update the linkTheme
        LinkTheme updatedLinkTheme = linkThemeRepository.findById(linkTheme.getId()).get();
        // Disconnect from session so that the updates on updatedLinkTheme are not directly saved in db
        em.detach(updatedLinkTheme);
        updatedLinkTheme.isCustom(UPDATED_IS_CUSTOM).customName(UPDATED_CUSTOM_NAME).presetTheme(UPDATED_PRESET_THEME);
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(updatedLinkTheme);

        restLinkThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkThemeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isOk());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
        LinkTheme testLinkTheme = linkThemeList.get(linkThemeList.size() - 1);
        assertThat(testLinkTheme.getIsCustom()).isEqualTo(UPDATED_IS_CUSTOM);
        assertThat(testLinkTheme.getCustomName()).isEqualTo(UPDATED_CUSTOM_NAME);
        assertThat(testLinkTheme.getPresetTheme()).isEqualTo(UPDATED_PRESET_THEME);
    }

    @Test
    @Transactional
    void putNonExistingLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkThemeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkThemeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkThemeWithPatch() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();

        // Update the linkTheme using partial update
        LinkTheme partialUpdatedLinkTheme = new LinkTheme();
        partialUpdatedLinkTheme.setId(linkTheme.getId());

        partialUpdatedLinkTheme.isCustom(UPDATED_IS_CUSTOM).customName(UPDATED_CUSTOM_NAME);

        restLinkThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkTheme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkTheme))
            )
            .andExpect(status().isOk());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
        LinkTheme testLinkTheme = linkThemeList.get(linkThemeList.size() - 1);
        assertThat(testLinkTheme.getIsCustom()).isEqualTo(UPDATED_IS_CUSTOM);
        assertThat(testLinkTheme.getCustomName()).isEqualTo(UPDATED_CUSTOM_NAME);
        assertThat(testLinkTheme.getPresetTheme()).isEqualTo(DEFAULT_PRESET_THEME);
    }

    @Test
    @Transactional
    void fullUpdateLinkThemeWithPatch() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();

        // Update the linkTheme using partial update
        LinkTheme partialUpdatedLinkTheme = new LinkTheme();
        partialUpdatedLinkTheme.setId(linkTheme.getId());

        partialUpdatedLinkTheme.isCustom(UPDATED_IS_CUSTOM).customName(UPDATED_CUSTOM_NAME).presetTheme(UPDATED_PRESET_THEME);

        restLinkThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinkTheme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinkTheme))
            )
            .andExpect(status().isOk());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
        LinkTheme testLinkTheme = linkThemeList.get(linkThemeList.size() - 1);
        assertThat(testLinkTheme.getIsCustom()).isEqualTo(UPDATED_IS_CUSTOM);
        assertThat(testLinkTheme.getCustomName()).isEqualTo(UPDATED_CUSTOM_NAME);
        assertThat(testLinkTheme.getPresetTheme()).isEqualTo(UPDATED_PRESET_THEME);
    }

    @Test
    @Transactional
    void patchNonExistingLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linkThemeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinkTheme() throws Exception {
        int databaseSizeBeforeUpdate = linkThemeRepository.findAll().size();
        linkTheme.setId(count.incrementAndGet());

        // Create the LinkTheme
        LinkThemeDTO linkThemeDTO = linkThemeMapper.toDto(linkTheme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkThemeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(linkThemeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinkTheme in the database
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinkTheme() throws Exception {
        // Initialize the database
        linkThemeRepository.saveAndFlush(linkTheme);

        int databaseSizeBeforeDelete = linkThemeRepository.findAll().size();

        // Delete the linkTheme
        restLinkThemeMockMvc
            .perform(delete(ENTITY_API_URL_ID, linkTheme.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinkTheme> linkThemeList = linkThemeRepository.findAll();
        assertThat(linkThemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
