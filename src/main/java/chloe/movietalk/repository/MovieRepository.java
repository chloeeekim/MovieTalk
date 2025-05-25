package chloe.movietalk.repository;

import chloe.movietalk.domain.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    List<Movie> findByTitleContaining(String keyword);

    Optional<Movie> findByCodeFIMS(String code);

    List<Movie> findByDirectorId(UUID directorId);

    @Override
    @EntityGraph(attributePaths = {"reviews"})
    Optional<Movie> findById(UUID id);
}
