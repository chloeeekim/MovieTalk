package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MovieRequest {

    @NotNull(message = "FIMS 코드가 입력되지 않았습니다.")
    private String codeFIMS;

    @NotBlank(message = "제목이 입력되지 않았습니다.")
    private String title;

    private String synopsis;
    private LocalDate releaseDate;
    private Integer prodYear;

    private Long directorId;

    @Builder
    public MovieRequest(String codeFIMS, String title, String synopsis, LocalDate releaseDate, Integer prodYear, Long directorId) {
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
        this.directorId = directorId;
    }

    public Movie toEntity(Director director) {
        return Movie.builder()
                .codeFIMS(this.codeFIMS)
                .title(this.title)
                .synopsis(this.synopsis)
                .releaseDate(this.releaseDate)
                .prodYear(this.prodYear)
                .director(director)
                .build();
    }
}
