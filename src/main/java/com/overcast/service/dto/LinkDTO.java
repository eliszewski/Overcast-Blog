package com.overcast.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.overcast.domain.Link} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LinkDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    private LinkThemeDTO linkTheme;

    private PostDTO post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkThemeDTO getLinkTheme() {
        return linkTheme;
    }

    public void setLinkTheme(LinkThemeDTO linkTheme) {
        this.linkTheme = linkTheme;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkDTO)) {
            return false;
        }

        LinkDTO linkDTO = (LinkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, linkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", linkTheme=" + getLinkTheme() +
            ", post=" + getPost() +
            "}";
    }
}
