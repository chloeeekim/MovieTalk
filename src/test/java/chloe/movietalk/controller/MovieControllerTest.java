package chloe.movietalk.controller;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequestDto;
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
        MovieRequestDto movie = MovieRequestDto.builder()
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
    @DisplayName("영화 수정")
    public void updateMovie() throws Exception {
        // given
        Movie movie = Movie.builder()
                .title("old title")
                .codeFIMS("111")
                .build();
        Movie save = movieRepository.save(movie);
        MovieRequestDto update = MovieRequestDto.builder()
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
}
