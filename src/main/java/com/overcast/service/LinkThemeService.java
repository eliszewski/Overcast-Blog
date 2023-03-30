package com.overcast.service;

import com.overcast.domain.LinkTheme;
import com.overcast.repository.LinkThemeRepository;
import com.overcast.service.dto.LinkThemeDTO;
import com.overcast.service.mapper.LinkThemeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LinkTheme}.
 */
@Service
@Transactional
public class LinkThemeService {

    private final Logger log = LoggerFactory.getLogger(LinkThemeService.class);

    private final LinkThemeRepository linkThemeRepository;

    private final LinkThemeMapper linkThemeMapper;

    public LinkThemeService(LinkThemeRepository linkThemeRepository, LinkThemeMapper linkThemeMapper) {
        this.linkThemeRepository = linkThemeRepository;
        this.linkThemeMapper = linkThemeMapper;
    }

    /**
     * Save a linkTheme.
     *
     * @param linkThemeDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkThemeDTO save(LinkThemeDTO linkThemeDTO) {
        log.debug("Request to save LinkTheme : {}", linkThemeDTO);
        LinkTheme linkTheme = linkThemeMapper.toEntity(linkThemeDTO);
        linkTheme = linkThemeRepository.save(linkTheme);
        return linkThemeMapper.toDto(linkTheme);
    }

    /**
     * Update a linkTheme.
     *
     * @param linkThemeDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkThemeDTO update(LinkThemeDTO linkThemeDTO) {
        log.debug("Request to update LinkTheme : {}", linkThemeDTO);
        LinkTheme linkTheme = linkThemeMapper.toEntity(linkThemeDTO);
        linkTheme = linkThemeRepository.save(linkTheme);
        return linkThemeMapper.toDto(linkTheme);
    }

    /**
     * Partially update a linkTheme.
     *
     * @param linkThemeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LinkThemeDTO> partialUpdate(LinkThemeDTO linkThemeDTO) {
        log.debug("Request to partially update LinkTheme : {}", linkThemeDTO);

        return linkThemeRepository
            .findById(linkThemeDTO.getId())
            .map(existingLinkTheme -> {
                linkThemeMapper.partialUpdate(existingLinkTheme, linkThemeDTO);

                return existingLinkTheme;
            })
            .map(linkThemeRepository::save)
            .map(linkThemeMapper::toDto);
    }

    /**
     * Get all the linkThemes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LinkThemeDTO> findAll() {
        log.debug("Request to get all LinkThemes");
        return linkThemeRepository.findAll().stream().map(linkThemeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the linkThemes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LinkThemeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return linkThemeRepository.findAllWithEagerRelationships(pageable).map(linkThemeMapper::toDto);
    }

    /**
     *  Get all the linkThemes where Link is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LinkThemeDTO> findAllWhereLinkIsNull() {
        log.debug("Request to get all linkThemes where Link is null");
        return StreamSupport
            .stream(linkThemeRepository.findAll().spliterator(), false)
            .filter(linkTheme -> linkTheme.getLink() == null)
            .map(linkThemeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one linkTheme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LinkThemeDTO> findOne(Long id) {
        log.debug("Request to get LinkTheme : {}", id);
        return linkThemeRepository.findOneWithEagerRelationships(id).map(linkThemeMapper::toDto);
    }

    /**
     * Delete the linkTheme by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LinkTheme : {}", id);
        linkThemeRepository.deleteById(id);
    }
}
