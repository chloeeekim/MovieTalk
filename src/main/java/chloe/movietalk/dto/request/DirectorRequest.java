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

    public static DirectorRequest fromEntity(Director director) {
        return DirectorRequest.builder()
                .name(director.getName())
                .gender(director.getGender())
                .country(director.getCountry())
                .build();
    }
}
