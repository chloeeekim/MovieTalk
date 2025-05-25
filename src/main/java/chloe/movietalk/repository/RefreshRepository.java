package chloe.movietalk.repository;

import chloe.movietalk.domain.Refresh;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRepository extends CrudRepository<Refresh, Long> {
    Refresh findByUserId(Long id);
}
