package com.overcast.service.mapper;

import com.overcast.domain.Blog;
import com.overcast.domain.Post;
import com.overcast.service.dto.BlogDTO;
import com.overcast.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "blog", source = "blog", qualifiedByName = "blogId")
    PostDTO toDto(Post s);

    @Named("blogId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BlogDTO toDtoBlogId(Blog blog);
}
