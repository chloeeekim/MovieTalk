package chloe.movietalk.dto.response.actor;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActorInfo {

    private Long id;
    private String name;
    private Gender gender;
    private String country;

    @Builder
    public ActorInfo(Long id, String name, Gender gender, String country) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public static ActorInfo fromEntity(Actor actor) {
        return ActorInfo.builder()
                .id(actor.getId())
                .name(actor.getName())
                .gender(actor.getGender())
                .country(actor.getCountry())
                .build();
    }
}
