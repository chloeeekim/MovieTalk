package chloe.movietalk.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

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

    @Builder
    public Movie(String codeFIMS, String title, String synopsis, LocalDate releaseDate, Integer prodYear) {
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
    }

    public void updateMovie(Movie movie) {
        this.codeFIMS = movie.getCodeFIMS();
        this.title = movie.getTitle();
        this.synopsis = movie.getSynopsis();
        this.releaseDate = movie.getReleaseDate();
        this.prodYear = movie.getProdYear();
    }
}