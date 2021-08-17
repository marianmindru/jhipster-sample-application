package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TranzactiiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranzactiiDTO.class);
        TranzactiiDTO tranzactiiDTO1 = new TranzactiiDTO();
        tranzactiiDTO1.setId(1L);
        TranzactiiDTO tranzactiiDTO2 = new TranzactiiDTO();
        assertThat(tranzactiiDTO1).isNotEqualTo(tranzactiiDTO2);
        tranzactiiDTO2.setId(tranzactiiDTO1.getId());
        assertThat(tranzactiiDTO1).isEqualTo(tranzactiiDTO2);
        tranzactiiDTO2.setId(2L);
        assertThat(tranzactiiDTO1).isNotEqualTo(tranzactiiDTO2);
        tranzactiiDTO1.setId(null);
        assertThat(tranzactiiDTO1).isNotEqualTo(tranzactiiDTO2);
    }
}
