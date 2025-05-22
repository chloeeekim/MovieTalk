package chloe.movietalk.repository;

import chloe.movietalk.domain.Actor;
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
public class ActorRepositoryTest {

    @Autowired
    ActorRepository actorRepository;

    @Test
    @DisplayName("배우 등록")
    public void createActor() {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();

        // when
        Actor save = actorRepository.save(actor);

        // then
        assertThat(save).isEqualTo(actor);
    }

    @Test
    @DisplayName("배우 목록 불러오기")
    public void actorList() {
        // given
        int count = 30;
        for (int i = 0; i < count; i++) {
            Actor actor = Actor.builder()
                    .name("배우 " + i)
                    .build();
            actorRepository.save(actor);
        }

        // when
        List<Actor> actorList = actorRepository.findAll();

        // then
        assertThat(actorList).hasSize(count);
    }

    @Test
    @DisplayName("배우 검색 : 아이디")
    public void findById() {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);

        // when
        Actor found = actorRepository.findById(save.getId()).get();

        // then
        assertThat(found).isEqualTo(actor);
    }

    @Test
    @DisplayName("배우 검색 : 이름")
    public void findByName() {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);

        // when
        String keyword = "배우";
        List<Actor> actorList = actorRepository.findByNameContaining(keyword);

        // then
        assertThat(actorList).containsOnly(actor);
    }
}
