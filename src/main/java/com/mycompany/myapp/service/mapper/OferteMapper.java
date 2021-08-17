package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OferteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Oferte} and its DTO {@link OferteDTO}.
 */
@Mapper(componentModel = "spring", uses = { UsersMapper.class })
public interface OferteMapper extends EntityMapper<OferteDTO, Oferte> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    OferteDTO toDto(Oferte s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OferteDTO toDtoId(Oferte oferte);
}
