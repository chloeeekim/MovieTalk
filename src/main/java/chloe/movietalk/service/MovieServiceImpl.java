package chloe.movietalk.service;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.movie.MovieDetailResponse;
import chloe.movietalk.dto.response.movie.MovieInfoResponse;
import chloe.movietalk.exception.actor.ActorNotFoundException;
import chloe.movietalk.exception.director.DirectorNotFoundException;
import chloe.movietalk.exception.movie.AlreadyExistsMovieException;
import chloe.movietalk.exception.movie.MovieNotFoundException;
import chloe.movietalk.repository.ActorRepository;
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
    private final ActorRepository actorRepository;

    @Override
    public List<MovieInfoResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDetailResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        return MovieDetailResponse.fromEntity(movie);
    }

    @Override
    public List<MovieInfoResponse> searchMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword).stream()
                .map(MovieInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MovieInfoResponse createMovie(MovieRequest request) {
        movieRepository.findByCodeFIMS(request.getCodeFIMS())
                .ifPresent(a -> {
                    throw AlreadyExistsMovieException.EXCEPTION;
                });

        Movie save = movieRepository.save(request.toEntity(getDirectorInfo(request.getDirectorId())));
        return MovieInfoResponse.fromEntity(save);
    }

    @Override
    public MovieInfoResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);

        movie.updateMovie(request.toEntity(getDirectorInfo(request.getDirectorId())));
        return MovieInfoResponse.fromEntity(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDetailResponse updateMovieActors(Long id, List<Long> actorIds) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);

        movie.getMovieActors().clear();

        actorIds.stream()
                .map(l -> actorRepository.findById(l).orElseThrow(() -> ActorNotFoundException.EXCEPTION))
                .forEach(movie::addActor);

        return MovieDetailResponse.fromEntity(movie);
    }

    @Override
    public MovieDetailResponse updateMovieDirector(Long id, Long directorId) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> MovieNotFoundException.EXCEPTION);

        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);

        movie.changeDirector(director);
        return MovieDetailResponse.fromEntity(movie);
    }

    private Director getDirectorInfo(Long id) {
        if (id == null) {
            return null;
        } else {
            return directorRepository.findById(id)
                    .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);
        }
    }
}
