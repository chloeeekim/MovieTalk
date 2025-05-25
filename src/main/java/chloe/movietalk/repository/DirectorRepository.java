package chloe.movietalk.repository;

import chloe.movietalk.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    List<Director> findByNameContaining(String keyword);
}
