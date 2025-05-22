package chloe.movietalk.service;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.MovieResponse;
import chloe.movietalk.exception.director.DirectorNotFoundException;
import chloe.movietalk.exception.movie.AlreadyExistsMovieException;
import chloe.movietalk.exception.movie.MovieNotFoundException;
import chloe.movietalk.repository.DirectorRepository;
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
    private final DirectorRepository directorRepository;

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        return MovieResponse.fromEntity(movie);
    }

    @Override
    public List<MovieResponse> searchMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword).stream()
                .map(MovieResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse createMovie(MovieRequest request) {
        movieRepository.findByCodeFIMS(request.getCodeFIMS())
                .ifPresent(a -> {
                    throw AlreadyExistsMovieException.EXCEPTION;
                });

        Movie save = movieRepository.save(request.toEntity(getDirector(request.getDirectorId())));
        return MovieResponse.fromEntity(save);
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);

        movie.updateMovie(request.toEntity(getDirector(request.getDirectorId())));
        return MovieResponse.fromEntity(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    private Director getDirector(Long id) {
        if (id == null) {
            return null;
        } else {
            return directorRepository.findById(id)
                    .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);
        }
    }
}
