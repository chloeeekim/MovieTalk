package chloe.movietalk.repository;

import chloe.movietalk.domain.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("영화 등록")
    public void createMovie() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화")
                .codeFIMS("123")
                .build();

        // when
        Movie save = movieRepository.save(movie);

        // then
        assertThat(save).isEqualTo(movie);
    }

    @Test
    @DisplayName("영화 목록 불러오기")
    public void movieList() {
        // given
        int count = 30;
        for (int i = 0; i < count; i++) {
            Movie movie = Movie.builder()
                    .title("영화 " + i)
                    .codeFIMS("code" + i)
                    .build();
            movieRepository.save(movie);
        }

        // when
        List<Movie> movieList = movieRepository.findAll();

        // then
        assertThat(movieList).hasSize(count);
    }

    @Test
    @DisplayName("영화 검색 : 아이디")
    public void findById() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화 제목")
                .codeFIMS("123123")
                .build();
        Movie save = movieRepository.save(movie);

        // when
        Movie found = movieRepository.findById(save.getId()).get();

        // then
        assertThat(found).isEqualTo(movie);
    }

    @Test
    @DisplayName("영화 검색 : 타이틀 키워드")
    public void findByTitle() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        // when
        String keyword = "테스트";
        List<Movie> movieList = movieRepository.findByTitleContaining(keyword);

        // then
        assertThat(movieList).containsOnly(movie);
    }

    @Test
    @DisplayName("영화 검색 : FIMS 코드")
    public void findByCodeFIMS() {
        // given
        Movie movie = Movie.builder()
                .title("테스트용 영화 제목")
                .codeFIMS("123123")
                .build();
        movieRepository.save(movie);

        // when
        Movie found = movieRepository.findByCodeFIMS("123123").get();

        // then
        assertThat(found).isEqualTo(movie);
    }
}
