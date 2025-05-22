package chloe.movietalk.dto.response.movie;

import chloe.movietalk.domain.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieInfo {

    private Long id;
    private String codeFIMS;
    private String title;

    @Builder
    public MovieInfo(Long id, String codeFIMS, String title) {
        this.id = id;
        this.codeFIMS = codeFIMS;
        this.title = title;
    }

    public static MovieInfo fromEntity(Movie movie) {
        return MovieInfo.builder()
                .id(movie.getId())
                .codeFIMS(movie.getCodeFIMS())
                .title(movie.getTitle())
                .build();
    }
}
