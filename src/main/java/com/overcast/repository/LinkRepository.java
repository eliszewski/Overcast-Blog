package com.overcast.repository;

import com.overcast.domain.Link;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Link entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {}
