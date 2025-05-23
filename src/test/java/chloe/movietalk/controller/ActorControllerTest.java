package chloe.movietalk.controller;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.enums.Gender;
import chloe.movietalk.dto.request.ActorRequest;
import chloe.movietalk.exception.actor.ActorErrorCode;
import chloe.movietalk.exception.global.GlobalErrorCode;
import chloe.movietalk.repository.ActorRepository;
import chloe.movietalk.repository.MovieActorRepository;
import chloe.movietalk.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ActorControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieActorRepository movieActorRepository;

    @Test
    @DisplayName("배우 목록 불러오기")
    public void getAllActors() throws Exception {
        // given
        int count = 2;
        for (int i = 0; i < count; i++) {
            Actor actor = Actor.builder()
                    .name("배우 " + i)
                    .build();
            actorRepository.save(actor);
            System.out.println("save");
        }

        // when
        ResultActions resultActions = mvc.perform(get("/api/actors"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].name").value("배우 0"))
                .andExpect(jsonPath("data[1].name").value("배우 1"));
    }

    @Test
    @DisplayName("배우 상세 정보")
    public void findActorById() throws Exception {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);

        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        actor.addMovie(movie);

        // when
        ResultActions resultActions = mvc.perform(get("/api/actors/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.name").value(actor.getName()))
                .andExpect(jsonPath("data.gender").value(actor.getGender().toString()))
                .andExpect(jsonPath("data.country").value(actor.getCountry()))
                .andExpect(jsonPath("data.filmography[0].title").value(movie.getTitle()));
    }

    @Test
    @DisplayName("배우 검색 : 이름 키워드")
    public void searchActors() throws Exception {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        actorRepository.save(actor);

        // when
        ResultActions resultActions = mvc.perform(get("/api/actors/search").param("keyword", "배우"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("data[0].name").value(actor.getName()));
    }

    @Test
    @DisplayName("배우 등록")
    public void createActor() throws Exception {
        // given
        ActorRequest actor = ActorRequest.builder()
                .name("김배우")
                .gender("MALE")
                .country("대한민국")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actor)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.name").value(actor.getName()))
                .andExpect(jsonPath("data.gender").value(actor.getGender()))
                .andExpect(jsonPath("data.country").value(actor.getCountry()));
    }

    @Test
    @DisplayName("배우 등록 실패 : 이름 미입력")
    public void createActorFailure1() throws Exception {
        // given
        ActorRequest actor = ActorRequest.builder()
                .gender("MALE")
                .country("대한민국")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actor)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("배우 등록 실패 : 잘못된 성별 값")
    public void createActorFailure2() throws Exception {
        // given
        ActorRequest actor = ActorRequest.builder()
                .name("김배우")
                .gender("WRONG")
                .country("대한민국")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actor)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_ENUM_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("배우 수정")
    public void updateActor() throws Exception {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);
        ActorRequest update = ActorRequest.builder()
                .name("이배우")
                .gender("FEMALE")
                .country("일본")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/actors/{id}", save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.name").value(update.getName()))
                .andExpect(jsonPath("data.gender").value(update.getGender()))
                .andExpect(jsonPath("data.country").value(update.getCountry()));
    }

    @Test
    @DisplayName("배우 수정 실패 : 존재하지 않는 배우")
    public void updateActorFailure1() throws Exception {
        // given
        ActorRequest update = ActorRequest.builder()
                .name("이배우")
                .gender("FEMALE")
                .country("일본")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/actors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));

        // then
        ActorErrorCode errorCode = ActorErrorCode.ACTOR_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("배우 삭제")
    public void deleteMovie() throws Exception {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/actors/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("배우 삭제 실패 : 존재하지 않는 배우")
    public void deleteActorFailure1() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(delete("/api/actors/{id}", 1L));

        // then
        ActorErrorCode errorCode = ActorErrorCode.ACTOR_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("배우 필모그라피 업데이트")
    public void updateActorFilmography() throws Exception {
        // given
        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Actor save = actorRepository.save(actor);

        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        // when
        ResultActions resultActions = mvc.perform(post("/api/actors/{id}/filmography", save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(movie.getId()))));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.name").value(actor.getName()))
                .andExpect(jsonPath("data.gender").value(actor.getGender().toString()))
                .andExpect(jsonPath("data.country").value(actor.getCountry()))
                .andExpect(jsonPath("data.filmography", hasSize(1)))
                .andExpect(jsonPath("data.filmography[0].title").value(movie.getTitle()));
    }
}
