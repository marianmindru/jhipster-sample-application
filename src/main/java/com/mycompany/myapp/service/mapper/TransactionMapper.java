package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { PolicyMapper.class, ClientMapper.class, OfferMapper.class })
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "policy", source = "policy", qualifiedByName = "id")
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    TransactionDTO toDto(Transaction s);
}
