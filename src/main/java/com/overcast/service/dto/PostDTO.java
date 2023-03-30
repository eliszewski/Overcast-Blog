package com.overcast.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.overcast.domain.Post} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTO implements Serializable {

    private Long id;

    private String header;

    @Lob
    private String content;

    @NotNull
    private ZonedDateTime date;

    private BlogDTO blog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BlogDTO getBlog() {
        return blog;
    }

    public void setBlog(BlogDTO blog) {
        this.blog = blog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", header='" + getHeader() + "'" +
            ", content='" + getContent() + "'" +
            ", date='" + getDate() + "'" +
            ", blog=" + getBlog() +
            "}";
    }
}
