package chloe.movietalk.controller;

import chloe.movietalk.domain.Actor;
import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.domain.enums.Gender;
import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.exception.director.DirectorErrorCode;
import chloe.movietalk.exception.global.GlobalErrorCode;
import chloe.movietalk.exception.movie.MovieErrorCode;
import chloe.movietalk.repository.ActorRepository;
import chloe.movietalk.repository.DirectorRepository;
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
public class MovieControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Test
    @DisplayName("영화 목록 불러오기")
    public void getAllMovies() throws Exception {
        // given
        int count = 2;
        for (int i = 0; i < count; i++) {
            Movie movie = Movie.builder()
                    .title("영화 " + i)
                    .codeFIMS("code" + i)
                    .build();
            movieRepository.save(movie);
        }

        // when
        ResultActions resultActions = mvc.perform(get("/api/movies"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].title").value("영화 0"))
                .andExpect(jsonPath("data[1].title").value("영화 1"));
    }

    @Test
    @DisplayName("영화 검색 : 아이디")
    public void findMovieById() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        Movie save = movieRepository.save(movie);

        // when
        ResultActions resultActions = mvc.perform(get("/api/movies/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value(movie.getTitle()))
                .andExpect(jsonPath("data.codeFIMS").value(movie.getCodeFIMS()));
    }

    @Test
    @DisplayName("영화 검색 : 타이틀 키워드")
    public void searchMovies() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        // when
        ResultActions resultActions = mvc.perform(get("/api/movies/search").param("keyword", "테스트"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("data[0].title").value(movie.getTitle()));
    }

    @Test
    @DisplayName("영화 등록")
    public void createMovie() throws Exception {
        // given
        MovieRequest movie = MovieRequest.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.title").value(movie.getTitle()))
                .andExpect(jsonPath("data.codeFIMS").value(movie.getCodeFIMS()));
    }

    @Test
    @DisplayName("영화 등록 실패 : 제목 미입력")
    public void createMovieFailure1() throws Exception {
        // given
        MovieRequest movie = MovieRequest.builder()
                .codeFIMS("123123")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("영화 등록 실패 : FIMS 코드 미입력")
    public void createMovieFailure2() throws Exception {
        // given
        MovieRequest movie = MovieRequest.builder()
                .title("테스트 영화 제목")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // then
        GlobalErrorCode errorCode = GlobalErrorCode.INVALID_FIELD_VALUE;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("영화 등록 실패 : 존재하지 않는 감독")
    public void createMovieFailure3() throws Exception {
        // given
        MovieRequest movie = MovieRequest.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .directorId(1L)
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        // then
        DirectorErrorCode errorCode = DirectorErrorCode.DIRECTOR_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("영화 수정")
    public void updateMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("old title")
                .codeFIMS("111")
                .build();
        Movie save = movieRepository.save(movie);
        MovieRequest update = MovieRequest.builder()
                .title("new title")
                .codeFIMS("222")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/movies/{id}", save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value(update.getTitle()))
                .andExpect(jsonPath("data.codeFIMS").value(update.getCodeFIMS()));
    }

    @Test
    @DisplayName("영화 수정 실패 : 존재하지 않는 영화")
    public void updateMovieFailure1() throws Exception {
        // given
        MovieRequest update = MovieRequest.builder()
                .title("new title")
                .codeFIMS("222")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/movies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));

        // then
        MovieErrorCode errorCode = MovieErrorCode.MOVIE_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("영화 삭제")
    public void deleteMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트 영화 제목")
                .codeFIMS("123123")
                .build();
        Movie save = movieRepository.save(movie);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/movies/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("영화 삭제 실패 : 존재하지 않는 영화")
    public void deleteMovieFailure1() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(delete("/api/movies/{id}", 1L));

        // then
        MovieErrorCode errorCode = MovieErrorCode.MOVIE_NOT_FOUND;
        resultActions
                .andExpect(status().is(errorCode.getStatus()))
                .andExpect(jsonPath("code").value(errorCode.getCode()));
    }

    @Test
    @DisplayName("영화 배우 목록 업데이트")
    public void updateActorsToMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        Movie save = movieRepository.save(movie);

        Actor actor = Actor.builder()
                .name("김배우")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        actorRepository.save(actor);

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies/{id}/actors", save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(actor.getId()))));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value(movie.getTitle()))
                .andExpect(jsonPath("data.codeFIMS").value(movie.getCodeFIMS()))
                .andExpect(jsonPath("data.actors", hasSize(1)))
                .andExpect(jsonPath("data.actors[0].name").value(actor.getName()));
    }

    @Test
    @DisplayName("영화 감독 업데이트")
    public void updateDirectorToMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();
        Movie save = movieRepository.save(movie);

        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        directorRepository.save(director);

        // when
        ResultActions resultActions = mvc.perform(post("/api/movies/{id}/director", save.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(director.getId())));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value(movie.getTitle()))
                .andExpect(jsonPath("data.codeFIMS").value(movie.getCodeFIMS()))
                .andExpect(jsonPath("data.director").isNotEmpty())
                .andExpect(jsonPath("data.director.name").value(director.getName()));
    }
}
