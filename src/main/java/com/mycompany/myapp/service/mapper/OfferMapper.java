package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClientMapper.class })
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    OfferDTO toDto(Offer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OfferDTO toDtoId(Offer offer);
}
