package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Tranzactii;
import com.mycompany.myapp.repository.TranzactiiRepository;
import com.mycompany.myapp.service.TranzactiiService;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import com.mycompany.myapp.service.mapper.TranzactiiMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tranzactii}.
 */
@Service
@Transactional
public class TranzactiiServiceImpl implements TranzactiiService {

    private final Logger log = LoggerFactory.getLogger(TranzactiiServiceImpl.class);

    private final TranzactiiRepository tranzactiiRepository;

    private final TranzactiiMapper tranzactiiMapper;

    public TranzactiiServiceImpl(TranzactiiRepository tranzactiiRepository, TranzactiiMapper tranzactiiMapper) {
        this.tranzactiiRepository = tranzactiiRepository;
        this.tranzactiiMapper = tranzactiiMapper;
    }

    @Override
    public TranzactiiDTO save(TranzactiiDTO tranzactiiDTO) {
        log.debug("Request to save Tranzactii : {}", tranzactiiDTO);
        Tranzactii tranzactii = tranzactiiMapper.toEntity(tranzactiiDTO);
        tranzactii = tranzactiiRepository.save(tranzactii);
        return tranzactiiMapper.toDto(tranzactii);
    }

    @Override
    public Optional<TranzactiiDTO> partialUpdate(TranzactiiDTO tranzactiiDTO) {
        log.debug("Request to partially update Tranzactii : {}", tranzactiiDTO);

        return tranzactiiRepository
            .findById(tranzactiiDTO.getId())
            .map(
                existingTranzactii -> {
                    tranzactiiMapper.partialUpdate(existingTranzactii, tranzactiiDTO);

                    return existingTranzactii;
                }
            )
            .map(tranzactiiRepository::save)
            .map(tranzactiiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TranzactiiDTO> findAll() {
        log.debug("Request to get all Tranzactiis");
        return tranzactiiRepository.findAll().stream().map(tranzactiiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TranzactiiDTO> findOne(Long id) {
        log.debug("Request to get Tranzactii : {}", id);
        return tranzactiiRepository.findById(id).map(tranzactiiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tranzactii : {}", id);
        tranzactiiRepository.deleteById(id);
    }
}
