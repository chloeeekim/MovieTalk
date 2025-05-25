package chloe.movietalk.repository;

import chloe.movietalk.domain.Refresh;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RefreshRepository extends CrudRepository<Refresh, UUID> {
    Refresh findByUserId(UUID id);
}
