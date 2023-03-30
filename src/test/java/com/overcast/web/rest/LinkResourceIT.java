package com.overcast.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.overcast.IntegrationTest;
import com.overcast.domain.Link;
import com.overcast.repository.LinkRepository;
import com.overcast.service.dto.LinkDTO;
import com.overcast.service.mapper.LinkMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LinkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinkResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/links";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinkMockMvc;

    private Link link;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Link createEntity(EntityManager em) {
        Link link = new Link().url(DEFAULT_URL);
        return link;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Link createUpdatedEntity(EntityManager em) {
        Link link = new Link().url(UPDATED_URL);
        return link;
    }

    @BeforeEach
    public void initTest() {
        link = createEntity(em);
    }

    @Test
    @Transactional
    void createLink() throws Exception {
        int databaseSizeBeforeCreate = linkRepository.findAll().size();
        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);
        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isCreated());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate + 1);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    void createLinkWithExistingId() throws Exception {
        // Create the Link with an existing ID
        link.setId(1L);
        LinkDTO linkDTO = linkMapper.toDto(link);

        int databaseSizeBeforeCreate = linkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setUrl(null);

        // Create the Link, which fails.
        LinkDTO linkDTO = linkMapper.toDto(link);

        restLinkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isBadRequest());

        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLinks() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get all the linkList
        restLinkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(link.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    void getLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get the link
        restLinkMockMvc
            .perform(get(ENTITY_API_URL_ID, link.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(link.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    void getNonExistingLink() throws Exception {
        // Get the link
        restLinkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link
        Link updatedLink = linkRepository.findById(link.getId()).get();
        // Disconnect from session so that the updates on updatedLink are not directly saved in db
        em.detach(updatedLink);
        updatedLink.url(UPDATED_URL);
        LinkDTO linkDTO = linkMapper.toDto(updatedLink);

        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkDTO))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void putNonExistingLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinkWithPatch() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link using partial update
        Link partialUpdatedLink = new Link();
        partialUpdatedLink.setId(link.getId());

        partialUpdatedLink.url(UPDATED_URL);

        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLink))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void fullUpdateLinkWithPatch() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link using partial update
        Link partialUpdatedLink = new Link();
        partialUpdatedLink.setId(link.getId());

        partialUpdatedLink.url(UPDATED_URL);

        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLink.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLink))
            )
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void patchNonExistingLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();
        link.setId(count.incrementAndGet());

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeDelete = linkRepository.findAll().size();

        // Delete the link
        restLinkMockMvc
            .perform(delete(ENTITY_API_URL_ID, link.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
