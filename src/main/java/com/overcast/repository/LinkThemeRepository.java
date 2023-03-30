package com.overcast.repository;

import com.overcast.domain.LinkTheme;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LinkTheme entity.
 */
@Repository
public interface LinkThemeRepository extends JpaRepository<LinkTheme, Long> {
    @Query("select linkTheme from LinkTheme linkTheme where linkTheme.user.login = ?#{principal.username}")
    List<LinkTheme> findByUserIsCurrentUser();

    default Optional<LinkTheme> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LinkTheme> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LinkTheme> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct linkTheme from LinkTheme linkTheme left join fetch linkTheme.user",
        countQuery = "select count(distinct linkTheme) from LinkTheme linkTheme"
    )
    Page<LinkTheme> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct linkTheme from LinkTheme linkTheme left join fetch linkTheme.user")
    List<LinkTheme> findAllWithToOneRelationships();

    @Query("select linkTheme from LinkTheme linkTheme left join fetch linkTheme.user where linkTheme.id =:id")
    Optional<LinkTheme> findOneWithToOneRelationships(@Param("id") Long id);
}
