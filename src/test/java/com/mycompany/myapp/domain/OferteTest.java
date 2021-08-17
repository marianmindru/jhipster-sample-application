package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OferteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oferte.class);
        Oferte oferte1 = new Oferte();
        oferte1.setId(1L);
        Oferte oferte2 = new Oferte();
        oferte2.setId(oferte1.getId());
        assertThat(oferte1).isEqualTo(oferte2);
        oferte2.setId(2L);
        assertThat(oferte1).isNotEqualTo(oferte2);
        oferte1.setId(null);
        assertThat(oferte1).isNotEqualTo(oferte2);
    }
}
