package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Tranzactii;
import com.mycompany.myapp.repository.TranzactiiRepository;
import com.mycompany.myapp.service.TranzactiiService;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import com.mycompany.myapp.service.mapper.TranzactiiMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<TranzactiiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tranzactiis");
        return tranzactiiRepository.findAll(pageable).map(tranzactiiMapper::toDto);
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
