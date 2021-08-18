package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TranzactiiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tranzactii.class);
        Tranzactii tranzactii1 = new Tranzactii();
        tranzactii1.setId(1L);
        Tranzactii tranzactii2 = new Tranzactii();
        tranzactii2.setId(tranzactii1.getId());
        assertThat(tranzactii1).isEqualTo(tranzactii2);
        tranzactii2.setId(2L);
        assertThat(tranzactii1).isNotEqualTo(tranzactii2);
        tranzactii1.setId(null);
        assertThat(tranzactii1).isNotEqualTo(tranzactii2);
    }
}
