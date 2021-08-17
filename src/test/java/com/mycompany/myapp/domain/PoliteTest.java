package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PoliteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Polite.class);
        Polite polite1 = new Polite();
        polite1.setId(1L);
        Polite polite2 = new Polite();
        polite2.setId(polite1.getId());
        assertThat(polite1).isEqualTo(polite2);
        polite2.setId(2L);
        assertThat(polite1).isNotEqualTo(polite2);
        polite1.setId(null);
        assertThat(polite1).isNotEqualTo(polite2);
    }
}
