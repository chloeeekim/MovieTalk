package chloe.movietalk.dto.response.director;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.enums.Gender;
import chloe.movietalk.dto.response.movie.MovieInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DirectorDetailResponse {

    private Long id;
    private String name;
    private Gender gender;
    private String country;
    private List<MovieInfo> filmography;

    @Builder
    public DirectorDetailResponse(Long id, String name, Gender gender, String country, List<MovieInfo> filmography) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.filmography = filmography;
    }

    public static DirectorDetailResponse fromEntity(Director director) {
        return DirectorDetailResponse.builder()
                .id(director.getId())
                .name(director.getName())
                .gender(director.getGender())
                .country(director.getCountry())
                .filmography(director.getFilmography().stream().map(MovieInfo::fromEntity).toList())
                .build();
    }
}
