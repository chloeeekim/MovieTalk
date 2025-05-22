package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Director;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DirectorResponse {

    private Long id;
    private String name;
    private String gender;
    private String country;
    private List<MovieInfo> filmography;

    @Builder
    public DirectorResponse(Long id, String name, String gender, String country, List<MovieInfo> filmography) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.filmography = filmography;
    }

    public static DirectorResponse fromEntity(Director director, List<MovieInfo> filmography) {
        return DirectorResponse.builder()
                .id(director.getId())
                .name(director.getName())
                .gender(director.getGender())
                .country(director.getCountry())
                .filmography(filmography)
                .build();
    }
}
