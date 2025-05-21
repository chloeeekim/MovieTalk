package chloe.movietalk.service;

import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequestDto;
import chloe.movietalk.dto.response.MovieDto;
import chloe.movietalk.exception.movie.AlreadyExistsMovieException;
import chloe.movietalk.exception.movie.MovieNotFoundException;
import chloe.movietalk.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        return MovieDto.fromEntity(movie);
    }

    @Override
    public List<MovieDto> searchMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword).stream()
                .map(MovieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto createMovie(MovieRequestDto dto) {
        movieRepository.findByCodeFIMS(dto.getCodeFIMS())
                .ifPresent(a -> {
                    throw AlreadyExistsMovieException.EXCEPTION;
                });

        Movie save = movieRepository.save(dto.toEntity());
        return MovieDto.fromEntity(save);
    }

    @Override
    public MovieDto updateMovie(Long id, MovieRequestDto dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        movie.updateMovie(dto.toEntity());
        return MovieDto.fromEntity(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}
