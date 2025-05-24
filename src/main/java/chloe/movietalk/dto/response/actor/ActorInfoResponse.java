package chloe.movietalk.dto.response.actor;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActorInfoResponse {

    @Schema(description = "배우 ID")
    private Long id;

    @Schema(description = "배우 이름")
    private String name;

    @Schema(description = "성별")
    private Gender gender;

    @Schema(description = "국적")
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
