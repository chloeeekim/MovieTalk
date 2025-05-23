package chloe.movietalk.controller;

import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.movie.MovieDetailResponse;
import chloe.movietalk.dto.response.movie.MovieInfoResponse;
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
    public List<MovieInfoResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDetailResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/search")
    public List<MovieInfoResponse> searchMovies(@RequestParam String keyword) {
        return movieService.searchMovies(keyword);
    }

    @PostMapping
    public ResponseEntity<MovieInfoResponse> createMovie(@RequestBody @Valid MovieRequest request) {
        MovieInfoResponse movie = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieInfoResponse> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieRequest request) {
        MovieInfoResponse movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok().body(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/actors")
    public ResponseEntity<MovieDetailResponse> updateActorsToMovie(@PathVariable Long id, @RequestBody List<Long> actorIds) {
        MovieDetailResponse movie = movieService.updateActorsToMovie(id, actorIds);
        return ResponseEntity.ok().body(movie);
    }

    @PostMapping("/{id}/director")
    public ResponseEntity<MovieDetailResponse> updateDirectorToMovie(@PathVariable Long id, @RequestBody Long directorId) {
        MovieDetailResponse movie = movieService.updateDirectorToMovie(id, directorId);
        return ResponseEntity.ok().body(movie);
    }
}
