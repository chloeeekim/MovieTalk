package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Actor create or update request")
public class ActorRequest {

    @NotBlank(message = "이름이 입력되지 않았습니다.")
    @Schema(description = "배우 이름")
    private String name;

    @Schema(description = "성별", allowableValues = {"MALE", "FEMALE", "OTHER"})
    private String gender;

    @Schema(description = "국적")
    private String country;

    @Builder
    public ActorRequest(String name, String gender, String country) {
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public Actor toEntity() {
        return Actor.builder()
                .name(this.name)
                .gender(Gender.from(this.gender))
                .country(this.country)
                .build();
    }
}
