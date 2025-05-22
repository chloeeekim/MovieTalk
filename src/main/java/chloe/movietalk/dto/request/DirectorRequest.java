package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectorRequest {

    private String name;
    private Gender gender;
    private String country;

    @Builder
    public DirectorRequest(String name, Gender gender, String country) {
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public Director toEntity() {
        return Director.builder()
                .name(this.name)
                .gender(this.gender)
                .country(this.country)
                .build();
    }
}
