package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActorRequest {

    @NotBlank(message = "이름이 입력되지 않았습니다.")
    private String name;
    private String gender;
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
