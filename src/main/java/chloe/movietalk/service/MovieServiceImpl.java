package chloe.movietalk.service;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequestDto;
import chloe.movietalk.dto.response.MovieDto;
import chloe.movietalk.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("영화를 찾을 수 없습니다."));
        return MovieDto.fromEntity(movie);
    }

    @Override
    public List<MovieDto> searchMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword).stream()
                .map(MovieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long createMovie(MovieRequestDto dto) {
        Movie save = movieRepository.save(dto.toEntity());
        return save.getId();
    }

    @Override
    public void updateMovie(Long id, MovieRequestDto dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("영화를 찾을 수 없습니다"));
        movie.updateMovie(dto.toEntity());
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}
