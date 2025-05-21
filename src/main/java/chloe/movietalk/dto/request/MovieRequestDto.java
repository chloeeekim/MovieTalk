package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MovieRequestDto {

    @NotNull
    private String codeFIMS;

    @NotNull
    private String title;

    private String synopsis;
    private LocalDate releaseDate;
    private Integer prodYear;

    private Director director;

    @Builder
    public MovieRequestDto(String codeFIMS, String title, String synopsis, LocalDate releaseDate, Integer prodYear, Director director) {
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
        this.director = director;
    }

    public Movie toEntity() {
        return Movie.builder()
                .codeFIMS(this.codeFIMS)
                .title(this.title)
                .synopsis(this.synopsis)
                .releaseDate(this.releaseDate)
                .prodYear(this.prodYear)
                .director(this.director)
                .build();
    }
}
