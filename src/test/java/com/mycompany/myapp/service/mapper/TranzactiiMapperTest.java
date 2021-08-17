package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranzactiiMapperTest {

    private TranzactiiMapper tranzactiiMapper;

    @BeforeEach
    public void setUp() {
        tranzactiiMapper = new TranzactiiMapperImpl();
    }
}
