package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OferteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Oferte} and its DTO {@link OferteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OferteMapper extends EntityMapper<OferteDTO, Oferte> {}
