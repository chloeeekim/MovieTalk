package chloe.movietalk.dto.response.actor;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import chloe.movietalk.dto.response.movie.MovieInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ActorDetailResponse {

    private Long id;
    private String name;
    private Gender gender;
    private String country;
    private List<MovieInfo> filmography;

    @Builder
    public ActorDetailResponse(Long id, String name, Gender gender, String country, List<MovieInfo> filmography) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.filmography = filmography;
    }

    public static ActorDetailResponse fromEntity(Actor actor) {
        return ActorDetailResponse.builder()
                .id(actor.getId())
                .name(actor.getName())
                .gender(actor.getGender())
                .country(actor.getCountry())
                .filmography(actor.getMovies().stream().map(MovieInfo::fromEntity).toList())
                .build();
    }
}
