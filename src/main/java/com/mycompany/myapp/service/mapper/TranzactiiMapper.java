package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tranzactii} and its DTO {@link TranzactiiDTO}.
 */
@Mapper(componentModel = "spring", uses = { OferteMapper.class, PoliteMapper.class, UsersMapper.class })
public interface TranzactiiMapper extends EntityMapper<TranzactiiDTO, Tranzactii> {
    @Mapping(target = "oferte", source = "oferte", qualifiedByName = "id")
    @Mapping(target = "polite", source = "polite", qualifiedByName = "id")
    @Mapping(target = "users", source = "users", qualifiedByName = "id")
    TranzactiiDTO toDto(Tranzactii s);
}
