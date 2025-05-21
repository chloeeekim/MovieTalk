package chloe.movietalk.domain;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String gender;

    @Column(length = 50)
    private String country;

    @Builder
    public Director(String name, String gender, String country) {
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public void updateDirector(Director director) {
        this.name = director.getName();
        this.gender = director.getGender();
        this.country = director.getCountry();
    }
}
