package com.overcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.overcast.domain.enumeration.Theme;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LinkTheme.
 */
@Entity
@Table(name = "link_theme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LinkTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "is_custom", nullable = false)
    private Boolean isCustom;

    @Column(name = "custom_name")
    private String customName;

    @Enumerated(EnumType.STRING)
    @Column(name = "preset_theme")
    private Theme presetTheme;

    @ManyToOne
    @JsonIgnoreProperties(value = { "links", "blog", "linkThemes" }, allowSetters = true)
    private Post post;

    @ManyToOne
    private User user;

    @JsonIgnoreProperties(value = { "linkTheme", "post", "ratings" }, allowSetters = true)
    @OneToOne(mappedBy = "linkTheme")
    private Link link;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LinkTheme id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCustom() {
        return this.isCustom;
    }

    public LinkTheme isCustom(Boolean isCustom) {
        this.setIsCustom(isCustom);
        return this;
    }

    public void setIsCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getCustomName() {
        return this.customName;
    }

    public LinkTheme customName(String customName) {
        this.setCustomName(customName);
        return this;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Theme getPresetTheme() {
        return this.presetTheme;
    }

    public LinkTheme presetTheme(Theme presetTheme) {
        this.setPresetTheme(presetTheme);
        return this;
    }

    public void setPresetTheme(Theme presetTheme) {
        this.presetTheme = presetTheme;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LinkTheme post(Post post) {
        this.setPost(post);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LinkTheme user(User user) {
        this.setUser(user);
        return this;
    }

    public Link getLink() {
        return this.link;
    }

    public void setLink(Link link) {
        if (this.link != null) {
            this.link.setLinkTheme(null);
        }
        if (link != null) {
            link.setLinkTheme(this);
        }
        this.link = link;
    }

    public LinkTheme link(Link link) {
        this.setLink(link);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkTheme)) {
            return false;
        }
        return id != null && id.equals(((LinkTheme) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinkTheme{" +
            "id=" + getId() +
            ", isCustom='" + getIsCustom() + "'" +
            ", customName='" + getCustomName() + "'" +
            ", presetTheme='" + getPresetTheme() + "'" +
            "}";
    }
}
