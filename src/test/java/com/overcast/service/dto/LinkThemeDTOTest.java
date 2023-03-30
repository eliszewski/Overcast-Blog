package com.overcast.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.overcast.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkThemeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkThemeDTO.class);
        LinkThemeDTO linkThemeDTO1 = new LinkThemeDTO();
        linkThemeDTO1.setId(1L);
        LinkThemeDTO linkThemeDTO2 = new LinkThemeDTO();
        assertThat(linkThemeDTO1).isNotEqualTo(linkThemeDTO2);
        linkThemeDTO2.setId(linkThemeDTO1.getId());
        assertThat(linkThemeDTO1).isEqualTo(linkThemeDTO2);
        linkThemeDTO2.setId(2L);
        assertThat(linkThemeDTO1).isNotEqualTo(linkThemeDTO2);
        linkThemeDTO1.setId(null);
        assertThat(linkThemeDTO1).isNotEqualTo(linkThemeDTO2);
    }
}
