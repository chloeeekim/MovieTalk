package chloe.movietalk.repository;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DirectorRepositoryTest {

    @Autowired
    DirectorRepository directorRepository;

    @Test
    @DisplayName("감독 등록")
    public void createDirector() {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();

        // when
        Director save = directorRepository.save(director);

        // then
        assertThat(save).isEqualTo(director);
    }

    @Test
    @DisplayName("감독 목록 불러오기")
    public void directorList() {
        // given
        int count = 30;
        for (int i = 0; i < count; i++) {
            Director director = Director.builder()
                    .name("감독" + i)
                    .build();
            directorRepository.save(director);
        }

        // when
        List<Director> directorList = directorRepository.findAll();

        // then
        assertThat(directorList).hasSize(count);
    }

    @Test
    @DisplayName("감독 검색 : 아이디")
    public void findById() {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Director save = directorRepository.save(director);

        // when
        Director found = directorRepository.findById(save.getId()).get();

        // then
        assertThat(found).isEqualTo(director);
    }

    @Test
    @DisplayName("감독 검색 : 이름")
    public void findByName() {
        // given
        Director director = Director.builder()
                .name("김감독")
                .build();
        directorRepository.save(director);

        // when
        String keyword = "감독";
        List<Director> directorList = directorRepository.findByNameContaining(keyword);

        // then
        assertThat(directorList).containsOnly(director);
    }
}
