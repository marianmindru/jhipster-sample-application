package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PoliteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoliteDTO.class);
        PoliteDTO politeDTO1 = new PoliteDTO();
        politeDTO1.setId(1L);
        PoliteDTO politeDTO2 = new PoliteDTO();
        assertThat(politeDTO1).isNotEqualTo(politeDTO2);
        politeDTO2.setId(politeDTO1.getId());
        assertThat(politeDTO1).isEqualTo(politeDTO2);
        politeDTO2.setId(2L);
        assertThat(politeDTO1).isNotEqualTo(politeDTO2);
        politeDTO1.setId(null);
        assertThat(politeDTO1).isNotEqualTo(politeDTO2);
    }
}
