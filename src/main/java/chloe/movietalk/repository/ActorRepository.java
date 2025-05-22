package chloe.movietalk.repository;

import chloe.movietalk.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByNameContaining(String keyword);
}
