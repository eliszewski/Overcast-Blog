package com.overcast.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.overcast.domain.Rating} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RatingDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer stars;

    private Instant date;

    private UserDTO user;

    private LinkDTO link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LinkDTO getLink() {
        return link;
    }

    public void setLink(LinkDTO link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingDTO)) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ratingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", stars=" + getStars() +
            ", date='" + getDate() + "'" +
            ", user=" + getUser() +
            ", link=" + getLink() +
            "}";
    }
}
