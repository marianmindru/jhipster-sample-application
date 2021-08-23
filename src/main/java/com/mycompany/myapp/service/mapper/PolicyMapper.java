package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Policy} and its DTO {@link PolicyDTO}.
 */
@Mapper(componentModel = "spring", uses = { OfferMapper.class, ClientMapper.class })
public interface PolicyMapper extends EntityMapper<PolicyDTO, Policy> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    PolicyDTO toDto(Policy s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PolicyDTO toDtoId(Policy policy);
}
