package chloe.movietalk.domain;

import chloe.movietalk.domain.enums.Gender;
import jakarta.persistence.Entity;
import lombok.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director extends Person {

    @Builder
    public Director(String name, Gender gender, String country) {
        super(name, gender, country);
    }

    public void updateDirector(Director director) {
        super.updatePerson(director.getName(), director.getGender(), director.getCountry());
    }
}
