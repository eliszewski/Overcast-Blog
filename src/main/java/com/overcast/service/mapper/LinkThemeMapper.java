package com.overcast.service.mapper;

import com.overcast.domain.LinkTheme;
import com.overcast.domain.Post;
import com.overcast.domain.User;
import com.overcast.service.dto.LinkThemeDTO;
import com.overcast.service.dto.PostDTO;
import com.overcast.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LinkTheme} and its DTO {@link LinkThemeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LinkThemeMapper extends EntityMapper<LinkThemeDTO, LinkTheme> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    LinkThemeDTO toDto(LinkTheme s);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
