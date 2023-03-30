package com.overcast.service;

import com.overcast.domain.Link;
import com.overcast.repository.LinkRepository;
import com.overcast.service.dto.LinkDTO;
import com.overcast.service.mapper.LinkMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Link}.
 */
@Service
@Transactional
public class LinkService {

    private final Logger log = LoggerFactory.getLogger(LinkService.class);

    private final LinkRepository linkRepository;

    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
    }

    /**
     * Save a link.
     *
     * @param linkDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkDTO save(LinkDTO linkDTO) {
        log.debug("Request to save Link : {}", linkDTO);
        Link link = linkMapper.toEntity(linkDTO);
        link = linkRepository.save(link);
        return linkMapper.toDto(link);
    }

    /**
     * Update a link.
     *
     * @param linkDTO the entity to save.
     * @return the persisted entity.
     */
    public LinkDTO update(LinkDTO linkDTO) {
        log.debug("Request to update Link : {}", linkDTO);
        Link link = linkMapper.toEntity(linkDTO);
        link = linkRepository.save(link);
        return linkMapper.toDto(link);
    }

    /**
     * Partially update a link.
     *
     * @param linkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LinkDTO> partialUpdate(LinkDTO linkDTO) {
        log.debug("Request to partially update Link : {}", linkDTO);

        return linkRepository
            .findById(linkDTO.getId())
            .map(existingLink -> {
                linkMapper.partialUpdate(existingLink, linkDTO);

                return existingLink;
            })
            .map(linkRepository::save)
            .map(linkMapper::toDto);
    }

    /**
     * Get all the links.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LinkDTO> findAll() {
        log.debug("Request to get all Links");
        return linkRepository.findAll().stream().map(linkMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one link by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LinkDTO> findOne(Long id) {
        log.debug("Request to get Link : {}", id);
        return linkRepository.findById(id).map(linkMapper::toDto);
    }

    /**
     * Delete the link by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Link : {}", id);
        linkRepository.deleteById(id);
    }
}
