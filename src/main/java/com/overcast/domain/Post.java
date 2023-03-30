package com.overcast.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "header")
    private String header;

    @Lob
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "linkTheme", "post", "ratings" }, allowSetters = true)
    private Set<Link> links = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "posts" }, allowSetters = true)
    private Blog blog;

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "post", "user", "link" }, allowSetters = true)
    private Set<LinkTheme> linkThemes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return this.header;
    }

    public Post header(String header) {
        this.setHeader(header);
        return this;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Post date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<Link> getLinks() {
        return this.links;
    }

    public void setLinks(Set<Link> links) {
        if (this.links != null) {
            this.links.forEach(i -> i.setPost(null));
        }
        if (links != null) {
            links.forEach(i -> i.setPost(this));
        }
        this.links = links;
    }

    public Post links(Set<Link> links) {
        this.setLinks(links);
        return this;
    }

    public Post addLink(Link link) {
        this.links.add(link);
        link.setPost(this);
        return this;
    }

    public Post removeLink(Link link) {
        this.links.remove(link);
        link.setPost(null);
        return this;
    }

    public Blog getBlog() {
        return this.blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Post blog(Blog blog) {
        this.setBlog(blog);
        return this;
    }

    public Set<LinkTheme> getLinkThemes() {
        return this.linkThemes;
    }

    public void setLinkThemes(Set<LinkTheme> linkThemes) {
        if (this.linkThemes != null) {
            this.linkThemes.forEach(i -> i.setPost(null));
        }
        if (linkThemes != null) {
            linkThemes.forEach(i -> i.setPost(this));
        }
        this.linkThemes = linkThemes;
    }

    public Post linkThemes(Set<LinkTheme> linkThemes) {
        this.setLinkThemes(linkThemes);
        return this;
    }

    public Post addLinkTheme(LinkTheme linkTheme) {
        this.linkThemes.add(linkTheme);
        linkTheme.setPost(this);
        return this;
    }

    public Post removeLinkTheme(LinkTheme linkTheme) {
        this.linkThemes.remove(linkTheme);
        linkTheme.setPost(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", header='" + getHeader() + "'" +
            ", content='" + getContent() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
