package chloe.movietalk.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_fims", length = 50, nullable = false)
    private String codeFIMS;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "prod_year")
    private Integer prodYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieActor> movieActors = new ArrayList<>();

    @Builder
    public Movie(String codeFIMS,
                 String title,
                 String synopsis,
                 LocalDate releaseDate,
                 Integer prodYear,
                 Director director) {
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
        this.director = director;
    }

    public void updateMovie(Movie movie) {
        this.codeFIMS = movie.getCodeFIMS();
        this.title = movie.getTitle();
        this.synopsis = movie.getSynopsis();
        this.releaseDate = movie.getReleaseDate();
        this.prodYear = movie.getProdYear();
        this.director = movie.getDirector();
        this.movieActors = movie.getMovieActors();
    }

    public List<Actor> getActors() {
        return movieActors.stream().map(MovieActor::getActor).toList();
    }

    public void addActor(Actor actor) {
        MovieActor movieActor = new MovieActor(this, actor);
        movieActors.add(movieActor);
        actor.getMovieActors().add(movieActor);
    }
}