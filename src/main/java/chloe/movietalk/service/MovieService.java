package chloe.movietalk.service;

import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {
    public List<MovieResponse> getAllMovies();

    public MovieResponse getMovieById(Long id);

    public List<MovieResponse> searchMovies(String keyword);

    public MovieResponse createMovie(MovieRequest dto);

    public MovieResponse updateMovie(Long id, MovieRequest dto);

    public void deleteMovie(Long id);
}
