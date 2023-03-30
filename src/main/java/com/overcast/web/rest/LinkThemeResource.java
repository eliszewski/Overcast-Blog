package com.overcast.web.rest;

import com.overcast.repository.LinkThemeRepository;
import com.overcast.service.LinkThemeService;
import com.overcast.service.dto.LinkThemeDTO;
import com.overcast.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.overcast.domain.LinkTheme}.
 */
@RestController
@RequestMapping("/api")
public class LinkThemeResource {

    private final Logger log = LoggerFactory.getLogger(LinkThemeResource.class);

    private static final String ENTITY_NAME = "linkTheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkThemeService linkThemeService;

    private final LinkThemeRepository linkThemeRepository;

    public LinkThemeResource(LinkThemeService linkThemeService, LinkThemeRepository linkThemeRepository) {
        this.linkThemeService = linkThemeService;
        this.linkThemeRepository = linkThemeRepository;
    }

    /**
     * {@code POST  /link-themes} : Create a new linkTheme.
     *
     * @param linkThemeDTO the linkThemeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkThemeDTO, or with status {@code 400 (Bad Request)} if the linkTheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/link-themes")
    public ResponseEntity<LinkThemeDTO> createLinkTheme(@Valid @RequestBody LinkThemeDTO linkThemeDTO) throws URISyntaxException {
        log.debug("REST request to save LinkTheme : {}", linkThemeDTO);
        if (linkThemeDTO.getId() != null) {
            throw new BadRequestAlertException("A new linkTheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkThemeDTO result = linkThemeService.save(linkThemeDTO);
        return ResponseEntity
            .created(new URI("/api/link-themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /link-themes/:id} : Updates an existing linkTheme.
     *
     * @param id the id of the linkThemeDTO to save.
     * @param linkThemeDTO the linkThemeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkThemeDTO,
     * or with status {@code 400 (Bad Request)} if the linkThemeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkThemeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/link-themes/{id}")
    public ResponseEntity<LinkThemeDTO> updateLinkTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LinkThemeDTO linkThemeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LinkTheme : {}, {}", id, linkThemeDTO);
        if (linkThemeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkThemeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkThemeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LinkThemeDTO result = linkThemeService.update(linkThemeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkThemeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /link-themes/:id} : Partial updates given fields of an existing linkTheme, field will ignore if it is null
     *
     * @param id the id of the linkThemeDTO to save.
     * @param linkThemeDTO the linkThemeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkThemeDTO,
     * or with status {@code 400 (Bad Request)} if the linkThemeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the linkThemeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the linkThemeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/link-themes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LinkThemeDTO> partialUpdateLinkTheme(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LinkThemeDTO linkThemeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LinkTheme partially : {}, {}", id, linkThemeDTO);
        if (linkThemeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linkThemeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linkThemeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LinkThemeDTO> result = linkThemeService.partialUpdate(linkThemeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linkThemeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /link-themes} : get all the linkThemes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkThemes in body.
     */
    @GetMapping("/link-themes")
    public List<LinkThemeDTO> getAllLinkThemes(
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("link-is-null".equals(filter)) {
            log.debug("REST request to get all LinkThemes where link is null");
            return linkThemeService.findAllWhereLinkIsNull();
        }
        log.debug("REST request to get all LinkThemes");
        return linkThemeService.findAll();
    }

    /**
     * {@code GET  /link-themes/:id} : get the "id" linkTheme.
     *
     * @param id the id of the linkThemeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkThemeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/link-themes/{id}")
    public ResponseEntity<LinkThemeDTO> getLinkTheme(@PathVariable Long id) {
        log.debug("REST request to get LinkTheme : {}", id);
        Optional<LinkThemeDTO> linkThemeDTO = linkThemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linkThemeDTO);
    }

    /**
     * {@code DELETE  /link-themes/:id} : delete the "id" linkTheme.
     *
     * @param id the id of the linkThemeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/link-themes/{id}")
    public ResponseEntity<Void> deleteLinkTheme(@PathVariable Long id) {
        log.debug("REST request to delete LinkTheme : {}", id);
        linkThemeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
