package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OferteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OferteDTO.class);
        OferteDTO oferteDTO1 = new OferteDTO();
        oferteDTO1.setId(1L);
        OferteDTO oferteDTO2 = new OferteDTO();
        assertThat(oferteDTO1).isNotEqualTo(oferteDTO2);
        oferteDTO2.setId(oferteDTO1.getId());
        assertThat(oferteDTO1).isEqualTo(oferteDTO2);
        oferteDTO2.setId(2L);
        assertThat(oferteDTO1).isNotEqualTo(oferteDTO2);
        oferteDTO1.setId(null);
        assertThat(oferteDTO1).isNotEqualTo(oferteDTO2);
    }
}
