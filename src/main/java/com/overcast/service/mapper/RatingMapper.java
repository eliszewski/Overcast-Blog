package com.overcast.service.mapper;

import com.overcast.domain.Link;
import com.overcast.domain.Rating;
import com.overcast.domain.User;
import com.overcast.service.dto.LinkDTO;
import com.overcast.service.dto.RatingDTO;
import com.overcast.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "link", source = "link", qualifiedByName = "linkId")
    RatingDTO toDto(Rating s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("linkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LinkDTO toDtoLinkId(Link link);
}
