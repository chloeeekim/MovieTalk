package chloe.movietalk.dto.response;

import chloe.movietalk.domain.Director;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectorInfoResponse {

    private Long id;
    private String name;
    private String gender;
    private String country;

    @Builder
    public DirectorInfoResponse(Long id, String name, String gender, String country) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public static DirectorInfoResponse fromEntity(Director director) {
        return DirectorInfoResponse.builder()
                .id(director.getId())
                .name(director.getName())
                .gender(director.getGender())
                .country(director.getCountry())
                .build();
    }
}
