package chloe.movietalk.controller;

import chloe.movietalk.dto.request.MovieRequestDto;
import chloe.movietalk.dto.response.MovieDto;
import chloe.movietalk.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/search")
    public List<MovieDto> searchMovies(@RequestParam String keyword) {
        return movieService.searchMovies(keyword);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid MovieRequestDto dto) {
        MovieDto movie = movieService.createMovie(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieRequestDto dto) {
        MovieDto movie = movieService.updateMovie(id, dto);
        return ResponseEntity.ok().body(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
