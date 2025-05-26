package chloe.movietalk.controller;

import chloe.movietalk.dto.request.MovieRequest;
import chloe.movietalk.dto.response.movie.MovieDetailResponse;
import chloe.movietalk.dto.response.movie.MovieInfoResponse;
import chloe.movietalk.dto.response.movie.UpdateMovieResponse;
import chloe.movietalk.exception.ErrorResponse;
import chloe.movietalk.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
@Tag(name = "Movie", description = "Movie APIs - 영화 목록 조회, 생성, 수정, 삭제 및 감독, 배우 목록 갱신 기능 제공")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies list", description = "모든 영화의 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = MovieInfoResponse.class)))})
    })
    public ResponseEntity<Page<MovieInfoResponse>> getAllMovies(
            @Parameter(name = "pageable", description = "페이지네이션 옵션")
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<MovieInfoResponse> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok().body(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get moviee by ID", description = "영화 ID로 특정 영화의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = MovieDetailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<MovieDetailResponse> getMovieById(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id
    ) {
        MovieDetailResponse movie = movieService.getMovieById(id);
        return ResponseEntity.ok().body(movie);
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies by keyword", description = "영화 제목에 키워드가 포함된 영화 목록을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = MovieInfoResponse.class)))})
    })
    public ResponseEntity<Page<MovieInfoResponse>> searchMovies(
            @Parameter(name = "keyword", description = "검색할 키워드")
            @RequestParam String keyword,

            @Parameter(name = "pageable", description = "페이지네이션 옵션")
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<MovieInfoResponse> movies = movieService.searchMovies(keyword, pageable);
        return ResponseEntity.ok().body(movies);
    }

    @PostMapping
    @Operation(summary = "Create new movie", description = "새로운 영화를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = MovieInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 영화입니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<MovieInfoResponse> createMovie(
            @Schema(implementation = MovieInfoResponse.class)
            @RequestBody @Valid MovieRequest request
    ) {
        MovieInfoResponse movie = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie by ID", description = "영화 ID로 기존 영화 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = MovieInfoResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<MovieInfoResponse> updateMovie(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id,

            @Schema(implementation = MovieRequest.class)
            @RequestBody @Valid MovieRequest request
    ) {
        MovieInfoResponse movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok().body(movie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie by ID", description = "영화 ID로 기존 영화 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공",
                    content = {}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<Void> deleteMovie(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id
    ) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/actors")
    @Operation(summary = "Update movie's actors list by ID", description = "영화 ID로 해당 영화의 배우 목록을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = UpdateMovieResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화 혹은 배우가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<UpdateMovieResponse> updateMovieActors(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "새로운 배우 목록으로 설정할 배우 ID 리스트",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))
            )
            @RequestBody List<UUID> actorIds
    ) {
        UpdateMovieResponse movie = movieService.updateMovieActors(id, actorIds);
        return ResponseEntity.ok().body(movie);
    }

    @PostMapping("/{id}/director")
    @Operation(summary = "Update movie's director by ID", description = "영화 ID로 해당 영화의 감독을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = UpdateMovieResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화 혹은 감독이 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<UpdateMovieResponse> updateMovieDirector(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "새로운 배우 목록으로 설정할 배우 ID 리스트",
                    content = @Content(schema = @Schema(implementation = Long.class))
            )
            @RequestBody UUID directorId
    ) {
        UpdateMovieResponse movie = movieService.updateMovieDirector(id, directorId);
        return ResponseEntity.ok().body(movie);
    }
}
