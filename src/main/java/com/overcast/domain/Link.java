package com.overcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Link.
 */
@Entity
@Table(name = "link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @JsonIgnoreProperties(value = { "post", "user", "link" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private LinkTheme linkTheme;

    @ManyToOne
    @JsonIgnoreProperties(value = { "links", "blog", "linkThemes" }, allowSetters = true)
    private Post post;

    @OneToMany(mappedBy = "link")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "link" }, allowSetters = true)
    private Set<Rating> ratings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Link id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public Link url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkTheme getLinkTheme() {
        return this.linkTheme;
    }

    public void setLinkTheme(LinkTheme linkTheme) {
        this.linkTheme = linkTheme;
    }

    public Link linkTheme(LinkTheme linkTheme) {
        this.setLinkTheme(linkTheme);
        return this;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Link post(Post post) {
        this.setPost(post);
        return this;
    }

    public Set<Rating> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        if (this.ratings != null) {
            this.ratings.forEach(i -> i.setLink(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setLink(this));
        }
        this.ratings = ratings;
    }

    public Link ratings(Set<Rating> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public Link addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setLink(this);
        return this;
    }

    public Link removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setLink(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Link)) {
            return false;
        }
        return id != null && id.equals(((Link) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Link{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
