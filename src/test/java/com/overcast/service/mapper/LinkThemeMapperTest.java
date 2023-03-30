package com.overcast.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkThemeMapperTest {

    private LinkThemeMapper linkThemeMapper;

    @BeforeEach
    public void setUp() {
        linkThemeMapper = new LinkThemeMapperImpl();
    }
}
