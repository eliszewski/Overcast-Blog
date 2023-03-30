package com.overcast.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.overcast.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkThemeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkTheme.class);
        LinkTheme linkTheme1 = new LinkTheme();
        linkTheme1.setId(1L);
        LinkTheme linkTheme2 = new LinkTheme();
        linkTheme2.setId(linkTheme1.getId());
        assertThat(linkTheme1).isEqualTo(linkTheme2);
        linkTheme2.setId(2L);
        assertThat(linkTheme1).isNotEqualTo(linkTheme2);
        linkTheme1.setId(null);
        assertThat(linkTheme1).isNotEqualTo(linkTheme2);
    }
}
