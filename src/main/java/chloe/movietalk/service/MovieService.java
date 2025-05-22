package chloe.movietalk.service;

import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {
    public List<MovieResponse> getAllMovies();

    public MovieResponse getMovieById(Long id);

    public List<MovieResponse> searchMovies(String keyword);

    public MovieResponse createMovie(MovieRequest request);

    public MovieResponse updateMovie(Long id, MovieRequest request);

    public void deleteMovie(Long id);
}
