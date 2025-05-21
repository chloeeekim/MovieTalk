package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MovieResponse {

    private Long id;
    private String codeFIMS;
    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private Integer prodYear;
    private DirectorInfo director;

    @Builder
    public MovieResponse(Long id, String codeFIMS, String title, String synopsis, LocalDate releaseDate, Integer prodYear, DirectorInfo director) {
        this.id = id;
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
        this.director = director;
    }

    public static MovieResponse fromEntity(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .codeFIMS(movie.getCodeFIMS())
                .title(movie.getTitle())
                .synopsis(movie.getSynopsis())
                .releaseDate(movie.getReleaseDate())
                .prodYear(movie.getProdYear())
                .director(movie.getDirector() == null ? null : DirectorInfo.fromEntity(movie.getDirector()))
                .build();
    }
}