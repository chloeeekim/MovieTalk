package chloe.movietalk.service;

import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.movie.MovieDetailResponse;
import chloe.movietalk.dto.response.movie.MovieInfoResponse;

import java.util.List;

public interface MovieService {
    public List<MovieInfoResponse> getAllMovies();

    public MovieDetailResponse getMovieById(Long id);

    public List<MovieInfoResponse> searchMovies(String keyword);

    public MovieInfoResponse createMovie(MovieRequest request);

    public MovieInfoResponse updateMovie(Long id, MovieRequest request);

    public void deleteMovie(Long id);
}
