package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tranzactii} and its DTO {@link TranzactiiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TranzactiiMapper extends EntityMapper<TranzactiiDTO, Tranzactii> {}
