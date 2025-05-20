package chloe.movietalk.service;

import chloe.movietalk.dto.request.MovieRequestDto;
import chloe.movietalk.dto.response.MovieDto;

import java.util.List;

public interface MovieService {
    public List<MovieDto> getAllMovies();

    public MovieDto getMovieById(Long id);

    public List<MovieDto> searchMovies(String keyword);

    public Long createMovie(MovieRequestDto dto);

    public void deleteMovie(Long id);
}
