package com.overcast.repository;

import com.overcast.domain.Rating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rating entity.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("select rating from Rating rating where rating.user.login = ?#{principal.username}")
    List<Rating> findByUserIsCurrentUser();

    default Optional<Rating> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Rating> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Rating> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct rating from Rating rating left join fetch rating.user",
        countQuery = "select count(distinct rating) from Rating rating"
    )
    Page<Rating> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct rating from Rating rating left join fetch rating.user")
    List<Rating> findAllWithToOneRelationships();

    @Query("select rating from Rating rating left join fetch rating.user where rating.id =:id")
    Optional<Rating> findOneWithToOneRelationships(@Param("id") Long id);
}
