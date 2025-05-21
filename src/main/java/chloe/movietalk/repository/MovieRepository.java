package chloe.movietalk.repository;

import chloe.movietalk.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String keyword);

    Optional<Movie> findByCodeFIMS(String code);

    List<Movie> findByDirectorId(Long director_id);
}
