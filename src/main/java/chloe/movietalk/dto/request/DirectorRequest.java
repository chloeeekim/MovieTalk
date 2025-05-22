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
    private String gender;
    private String country;

    @Builder
    public DirectorRequest(String name, String gender, String country) {
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public Director toEntity() {
        return Director.builder()
                .name(this.name)
                .gender(Gender.from(this.gender))
                .country(this.country)
                .build();
    }
}
