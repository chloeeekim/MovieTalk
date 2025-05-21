package chloe.movietalk.service;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequestDto;
import chloe.movietalk.dto.response.MovieDto;
import chloe.movietalk.exception.movie.AlreadyExistsMovieException;
import chloe.movietalk.repository.DirectorRepository;
import chloe.movietalk.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    MovieService movieService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    DirectorRepository directorRepository;

    @BeforeEach
    public void beforeEach() {
        movieService = new MovieServiceImpl(movieRepository, directorRepository);
    }

    @Test
    @DisplayName("영화 생성 성공")
    public void createMovieSuccess() {
        // given
        MovieRequestDto requestDto = MovieRequestDto.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();

        given(movieRepository.save(any(Movie.class)))
                .willReturn(requestDto.toEntity(null));

        // when
        MovieDto movie = movieService.createMovie(requestDto);

        // then
        assertThat(movie.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(movie.getCodeFIMS()).isEqualTo(requestDto.getCodeFIMS());
    }

    @Test
    @DisplayName("영화 생성 실패 - code 중복")
    public void createMovieException() {
        // given
        MovieRequestDto requestDto = MovieRequestDto.builder()
                .title("테스트용 영화")
                .codeFIMS("123123")
                .build();

        given(movieRepository.findByCodeFIMS("123123"))
                .willReturn(Optional.of(requestDto.toEntity(null)));

        // when then
        Assertions.assertThrows(AlreadyExistsMovieException.class,
                () -> movieService.createMovie(requestDto));
    }
}
