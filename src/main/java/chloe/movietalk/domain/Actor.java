package chloe.movietalk.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Actor extends Person {

    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE)
    private List<MovieActor> movieActors;

    @Builder
    public Actor(String name, Gender gender, String country, List<MovieActor> movieActors) {
        super(name, gender, country);
        this.movieActors = movieActors;
    }

    public void updateActor(Actor actor) {
        super.updatePerson(actor.getName(), actor.getGender(), actor.getCountry());
        this.movieActors = actor.getMovieActors();
    }

    public List<Movie> getMovies() {
        return movieActors == null ? null : movieActors.stream().map((MovieActor::getMovie)).toList();
    }
}
