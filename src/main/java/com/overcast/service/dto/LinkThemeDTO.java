package com.overcast.service.dto;

import com.overcast.domain.enumeration.Theme;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.overcast.domain.LinkTheme} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LinkThemeDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean isCustom;

    private String customName;

    private Theme presetTheme;

    private PostDTO post;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Theme getPresetTheme() {
        return presetTheme;
    }

    public void setPresetTheme(Theme presetTheme) {
        this.presetTheme = presetTheme;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkThemeDTO)) {
            return false;
        }

        LinkThemeDTO linkThemeDTO = (LinkThemeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, linkThemeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkThemeDTO{" +
            "id=" + getId() +
            ", isCustom='" + getIsCustom() + "'" +
            ", customName='" + getCustomName() + "'" +
            ", presetTheme='" + getPresetTheme() + "'" +
            ", post=" + getPost() +
            ", user=" + getUser() +
            "}";
    }
}
