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
    @DisplayName("타이틀 키워드로 검색")
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
}
