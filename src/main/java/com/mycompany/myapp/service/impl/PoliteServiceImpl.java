package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Polite;
import com.mycompany.myapp.repository.PoliteRepository;
import com.mycompany.myapp.service.PoliteService;
import com.mycompany.myapp.service.dto.PoliteDTO;
import com.mycompany.myapp.service.mapper.PoliteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Polite}.
 */
@Service
@Transactional
public class PoliteServiceImpl implements PoliteService {

    private final Logger log = LoggerFactory.getLogger(PoliteServiceImpl.class);

    private final PoliteRepository politeRepository;

    private final PoliteMapper politeMapper;

    public PoliteServiceImpl(PoliteRepository politeRepository, PoliteMapper politeMapper) {
        this.politeRepository = politeRepository;
        this.politeMapper = politeMapper;
    }

    @Override
    public PoliteDTO save(PoliteDTO politeDTO) {
        log.debug("Request to save Polite : {}", politeDTO);
        Polite polite = politeMapper.toEntity(politeDTO);
        polite = politeRepository.save(polite);
        return politeMapper.toDto(polite);
    }

    @Override
    public Optional<PoliteDTO> partialUpdate(PoliteDTO politeDTO) {
        log.debug("Request to partially update Polite : {}", politeDTO);

        return politeRepository
            .findById(politeDTO.getId())
            .map(
                existingPolite -> {
                    politeMapper.partialUpdate(existingPolite, politeDTO);

                    return existingPolite;
                }
            )
            .map(politeRepository::save)
            .map(politeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PoliteDTO> findAll() {
        log.debug("Request to get all Polites");
        return politeRepository.findAll().stream().map(politeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the polites where Tranzactie is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PoliteDTO> findAllWhereTranzactieIsNull() {
        log.debug("Request to get all polites where Tranzactie is null");
        return StreamSupport
            .stream(politeRepository.findAll().spliterator(), false)
            .filter(polite -> polite.getTranzactie() == null)
            .map(politeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PoliteDTO> findOne(Long id) {
        log.debug("Request to get Polite : {}", id);
        return politeRepository.findById(id).map(politeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Polite : {}", id);
        politeRepository.deleteById(id);
    }
}
