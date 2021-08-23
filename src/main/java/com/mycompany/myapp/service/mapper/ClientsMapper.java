package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ClientsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clients} and its DTO {@link ClientsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientsMapper extends EntityMapper<ClientsDTO, Clients> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientsDTO toDtoId(Clients clients);
}
