package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MovieDto {

    private String codeFIMS;
    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private Integer prodYear;

    @Builder
    public MovieDto(String codeFIMS, String title, String synopsis, LocalDate releaseDate, Integer prodYear) {
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
    }

    public static MovieDto fromEntity(Movie movie) {
        return MovieDto.builder()
                .codeFIMS(movie.getCodeFIMS())
                .title(movie.getTitle())
                .synopsis(movie.getSynopsis())
                .releaseDate(movie.getReleaseDate())
                .prodYear(movie.getProdYear())
                .build();
    }
}