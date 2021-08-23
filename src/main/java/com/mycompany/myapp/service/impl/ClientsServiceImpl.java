package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Clients;
import com.mycompany.myapp.repository.ClientsRepository;
import com.mycompany.myapp.service.ClientsService;
import com.mycompany.myapp.service.dto.ClientsDTO;
import com.mycompany.myapp.service.mapper.ClientsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Clients}.
 */
@Service
@Transactional
public class ClientsServiceImpl implements ClientsService {

    private final Logger log = LoggerFactory.getLogger(ClientsServiceImpl.class);

    private final ClientsRepository clientsRepository;

    private final ClientsMapper clientsMapper;

    public ClientsServiceImpl(ClientsRepository clientsRepository, ClientsMapper clientsMapper) {
        this.clientsRepository = clientsRepository;
        this.clientsMapper = clientsMapper;
    }

    @Override
    public ClientsDTO save(ClientsDTO clientsDTO) {
        log.debug("Request to save Clients : {}", clientsDTO);
        Clients clients = clientsMapper.toEntity(clientsDTO);
        clients = clientsRepository.save(clients);
        return clientsMapper.toDto(clients);
    }

    @Override
    public Optional<ClientsDTO> partialUpdate(ClientsDTO clientsDTO) {
        log.debug("Request to partially update Clients : {}", clientsDTO);

        return clientsRepository
            .findById(clientsDTO.getId())
            .map(
                existingClients -> {
                    clientsMapper.partialUpdate(existingClients, clientsDTO);

                    return existingClients;
                }
            )
            .map(clientsRepository::save)
            .map(clientsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientsDTO> findAll() {
        log.debug("Request to get all Clients");
        return clientsRepository.findAll().stream().map(clientsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientsDTO> findOne(Long id) {
        log.debug("Request to get Clients : {}", id);
        return clientsRepository.findById(id).map(clientsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clients : {}", id);
        clientsRepository.deleteById(id);
    }
}
