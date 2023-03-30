package com.overcast.service.mapper;

import com.overcast.domain.Link;
import com.overcast.domain.LinkTheme;
import com.overcast.domain.Post;
import com.overcast.service.dto.LinkDTO;
import com.overcast.service.dto.LinkThemeDTO;
import com.overcast.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Link} and its DTO {@link LinkDTO}.
 */
@Mapper(componentModel = "spring")
public interface LinkMapper extends EntityMapper<LinkDTO, Link> {
    @Mapping(target = "linkTheme", source = "linkTheme", qualifiedByName = "linkThemeId")
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    LinkDTO toDto(Link s);

    @Named("linkThemeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LinkThemeDTO toDtoLinkThemeId(LinkTheme linkTheme);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
