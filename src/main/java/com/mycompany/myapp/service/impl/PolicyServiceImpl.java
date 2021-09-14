package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.PolicyRepository;
import com.mycompany.myapp.service.PolicyService;
import com.mycompany.myapp.service.dto.PolicyDTO;
import com.mycompany.myapp.service.mapper.PolicyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Policy}.
 */
@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final Logger log = LoggerFactory.getLogger(PolicyServiceImpl.class);

    private final PolicyRepository policyRepository;

    private final PolicyMapper policyMapper;

    public PolicyServiceImpl(PolicyRepository policyRepository, PolicyMapper policyMapper) {
        this.policyRepository = policyRepository;
        this.policyMapper = policyMapper;
    }

    @Override
    public PolicyDTO save(PolicyDTO policyDTO) {
        log.debug("Request to save Policy : {}", policyDTO);
        Policy policy = policyMapper.toEntity(policyDTO);
        policy = policyRepository.save(policy);
        return policyMapper.toDto(policy);
    }

    @Override
    public Optional<PolicyDTO> partialUpdate(PolicyDTO policyDTO) {
        log.debug("Request to partially update Policy : {}", policyDTO);

        return policyRepository
            .findById(policyDTO.getId())
            .map(
                existingPolicy -> {
                    policyMapper.partialUpdate(existingPolicy, policyDTO);

                    return existingPolicy;
                }
            )
            .map(policyRepository::save)
            .map(policyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolicyDTO> findAll() {
        log.debug("Request to get all Policies");
        return policyRepository.findAll().stream().map(policyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PolicyDTO> findOne(Long id) {
        log.debug("Request to get Policy : {}", id);
        return policyRepository.findById(id).map(policyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Policy : {}", id);
        policyRepository.deleteById(id);
    }
}
