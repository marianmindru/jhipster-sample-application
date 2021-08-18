package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Oferte;
import com.mycompany.myapp.repository.OferteRepository;
import com.mycompany.myapp.service.OferteService;
import com.mycompany.myapp.service.dto.OferteDTO;
import com.mycompany.myapp.service.mapper.OferteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Oferte}.
 */
@Service
@Transactional
public class OferteServiceImpl implements OferteService {

    private final Logger log = LoggerFactory.getLogger(OferteServiceImpl.class);

    private final OferteRepository oferteRepository;

    private final OferteMapper oferteMapper;

    public OferteServiceImpl(OferteRepository oferteRepository, OferteMapper oferteMapper) {
        this.oferteRepository = oferteRepository;
        this.oferteMapper = oferteMapper;
    }

    @Override
    public OferteDTO save(OferteDTO oferteDTO) {
        log.debug("Request to save Oferte : {}", oferteDTO);
        Oferte oferte = oferteMapper.toEntity(oferteDTO);
        oferte = oferteRepository.save(oferte);
        return oferteMapper.toDto(oferte);
    }

    @Override
    public Optional<OferteDTO> partialUpdate(OferteDTO oferteDTO) {
        log.debug("Request to partially update Oferte : {}", oferteDTO);

        return oferteRepository
            .findById(oferteDTO.getId())
            .map(
                existingOferte -> {
                    oferteMapper.partialUpdate(existingOferte, oferteDTO);

                    return existingOferte;
                }
            )
            .map(oferteRepository::save)
            .map(oferteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OferteDTO> findAll() {
        log.debug("Request to get all Ofertes");
        return oferteRepository.findAll().stream().map(oferteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OferteDTO> findOne(Long id) {
        log.debug("Request to get Oferte : {}", id);
        return oferteRepository.findById(id).map(oferteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Oferte : {}", id);
        oferteRepository.deleteById(id);
    }
}
