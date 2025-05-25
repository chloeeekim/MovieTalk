package chloe.movietalk.repository;

import chloe.movietalk.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Refresh findByUserId(Long id);
}
