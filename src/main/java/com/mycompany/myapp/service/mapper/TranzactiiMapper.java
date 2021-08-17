package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tranzactii} and its DTO {@link TranzactiiDTO}.
 */
@Mapper(componentModel = "spring", uses = { OferteMapper.class, PoliteMapper.class, UsersMapper.class })
public interface TranzactiiMapper extends EntityMapper<TranzactiiDTO, Tranzactii> {
    @Mapping(target = "o", source = "o", qualifiedByName = "id")
    @Mapping(target = "p", source = "p", qualifiedByName = "id")
    @Mapping(target = "u", source = "u", qualifiedByName = "id")
    TranzactiiDTO toDto(Tranzactii s);
}
