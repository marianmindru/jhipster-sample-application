package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PoliteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Polite} and its DTO {@link PoliteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClientsMapper.class })
public interface PoliteMapper extends EntityMapper<PoliteDTO, Polite> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    PoliteDTO toDto(Polite s);
}
