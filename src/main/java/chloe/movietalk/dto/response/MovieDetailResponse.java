package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class MovieDetailResponse {

    private Long id;
    private String codeFIMS;
    private String title;
    private String synopsis;
    private LocalDate releaseDate;
    private Integer prodYear;
    private DirectorInfo director;
    private List<ActorInfo> actors;

    @Builder
    public MovieDetailResponse(Long id,
                               String codeFIMS,
                               String title,
                               String synopsis,
                               LocalDate releaseDate,
                               Integer prodYear,
                               DirectorInfo director,
                               List<ActorInfo> actors) {
        this.id = id;
        this.codeFIMS = codeFIMS;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.prodYear = prodYear;
        this.director = director;
        this.actors = actors;
    }

    public static MovieDetailResponse fromEntity(Movie movie) {
        return MovieDetailResponse.builder()
                .id(movie.getId())
                .codeFIMS(movie.getCodeFIMS())
                .title(movie.getTitle())
                .synopsis(movie.getSynopsis())
                .releaseDate(movie.getReleaseDate())
                .prodYear(movie.getProdYear())
                .director(movie.getDirector() == null ? null : DirectorInfo.fromEntity(movie.getDirector()))
                .actors(movie.getActors().stream().map(ActorInfo::fromEntity).toList())
                .build();
    }
}
