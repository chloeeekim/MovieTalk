package chloe.movietalk.dto.request;

import chloe.movietalk.domain.Director;
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
                .gender(this.gender)
                .country(this.country)
                .build();
    }
}
