package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tranzactii} and its DTO {@link TranzactiiDTO}.
 */
@Mapper(componentModel = "spring", uses = { OferteMapper.class, PoliteMapper.class, UsersMapper.class })
public interface TranzactiiMapper extends EntityMapper<TranzactiiDTO, Tranzactii> {
    @Mapping(target = "oferta", source = "oferta", qualifiedByName = "id")
    @Mapping(target = "polita", source = "polita", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    TranzactiiDTO toDto(Tranzactii s);
}
