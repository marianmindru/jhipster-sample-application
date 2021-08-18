package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OferteMapperTest {

    private OferteMapper oferteMapper;

    @BeforeEach
    public void setUp() {
        oferteMapper = new OferteMapperImpl();
    }
}
