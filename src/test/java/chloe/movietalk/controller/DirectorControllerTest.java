package chloe.movietalk.controller;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Gender;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.DirectorRequest;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DirectorControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;

    @Test
    @DisplayName("감독 목록 불러오기")
    public void getAllDirectors() throws Exception {
        // given
        int count = 2;
        for (int i = 0; i < count; i++) {
            Director director = Director.builder()
                    .name("감독 " + i)
                    .build();
            directorRepository.save(director);
        }

        // when
        ResultActions resultActions = mvc.perform(get("/api/directors"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].name").value("감독 0"))
                .andExpect(jsonPath("data[1].name").value("감독 1"));
    }

    @Test
    @DisplayName("감독 상세 정보")
    public void findDirectorById() throws Exception {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Director save = directorRepository.save(director);

        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .director(director)
                .build();
        movieRepository.save(movie);

        // when
        ResultActions resultActions = mvc.perform(get("/api/directors/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.name").value(director.getName()))
                .andExpect(jsonPath("data.gender").value(director.getGender().toString()))
                .andExpect(jsonPath("data.country").value(director.getCountry()))
                .andExpect(jsonPath("data.filmography[0].title").value(movie.getTitle()));
    }

    @Test
    @DisplayName("감독 검색 : 이름 키워드")
    public void searchDirectors() throws Exception {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        directorRepository.save(director);

        // when
        ResultActions resultActions = mvc.perform(get("/api/directors/search").param("keyword", "감독"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("data[0].name").value(director.getName()));
    }

    @Test
    @DisplayName("감독 등록")
    public void createDirector() throws Exception {
        // given
        DirectorRequest director = DirectorRequest.builder()
                .name("김감독")
                .gender("MALE")
                .country("대한민국")
                .build();

        // when
        ResultActions resultActions = mvc.perform(post("/api/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(director)));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.name").value(director.getName()))
                .andExpect(jsonPath("data.gender").value(director.getGender()))
                .andExpect(jsonPath("data.country").value(director.getCountry()));
    }

    @Test
    @DisplayName("감독 수정")
    public void updateDirector() throws Exception {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Director save = directorRepository.save(director);
        DirectorRequest update = DirectorRequest.builder()
                .name("이감독")
                .gender("FEMALE")
                .country("일본")
                .build();

        // when
        ResultActions resultActions = mvc.perform(put("/api/directors/{id}", save.getId())
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
    @DisplayName("감독 삭제")
    public void deleteMovie() throws Exception {
        // given
        Director director = Director.builder()
                .name("김감독")
                .gender(Gender.MALE)
                .country("대한민국")
                .build();
        Director save = directorRepository.save(director);

        // when
        ResultActions resultActions = mvc.perform(delete("/api/directors/{id}", save.getId()));

        // then
        resultActions
                .andExpect(status().isNoContent());
    }
}
