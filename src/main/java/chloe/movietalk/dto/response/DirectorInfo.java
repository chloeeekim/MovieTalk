package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectorInfo {

    private Long id;
    private String name;
    private Gender gender;
    private String country;

    @Builder
    public DirectorInfo(Long id, String name, Gender gender, String country) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public static DirectorInfo fromEntity(Director director) {
        return DirectorInfo.builder()
                .id(director.getId())
                .name(director.getName())
                .gender(director.getGender())
                .country(director.getCountry())
                .build();
    }
}
