package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Users;
import com.mycompany.myapp.repository.UsersRepository;
import com.mycompany.myapp.service.UsersService;
import com.mycompany.myapp.service.dto.UsersDTO;
import com.mycompany.myapp.service.mapper.UsersMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    @Override
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    @Override
    public Optional<UsersDTO> partialUpdate(UsersDTO usersDTO) {
        log.debug("Request to partially update Users : {}", usersDTO);

        return usersRepository
            .findById(usersDTO.getId())
            .map(
                existingUsers -> {
                    usersMapper.partialUpdate(existingUsers, usersDTO);

                    return existingUsers;
                }
            )
            .map(usersRepository::save)
            .map(usersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersDTO> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll().stream().map(usersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsersDTO> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id).map(usersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}
