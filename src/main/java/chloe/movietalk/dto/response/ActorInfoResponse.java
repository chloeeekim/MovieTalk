package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActorInfoResponse {

    private Long id;
    private String name;
    private Gender gender;
    private String country;

    @Builder
    public ActorInfoResponse(Long id, String name, Gender gender, String country) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public static ActorInfoResponse fromEntity(Actor actor) {
        return ActorInfoResponse.builder()
                .id(actor.getId())
                .name(actor.getName())
                .gender(actor.getGender())
                .country(actor.getCountry())
                .build();
    }
}
