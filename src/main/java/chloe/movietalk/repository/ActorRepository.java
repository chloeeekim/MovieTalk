package chloe.movietalk.repository;

import chloe.movietalk.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByNameContaining(String keyword);
}
