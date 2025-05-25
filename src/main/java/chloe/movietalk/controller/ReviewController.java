package chloe.movietalk.controller;

import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.dto.response.review.ReviewByMovieResponse;
import chloe.movietalk.dto.response.review.ReviewByUserResponse;
import chloe.movietalk.dto.response.review.ReviewDetailResponse;
import chloe.movietalk.exception.ErrorResponse;
import chloe.movietalk.service.ReviewService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "Review APIs - 리뷰 목록 조회, 생성, 수정, 삭제 기능 제공")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create new review", description = "새로운 리뷰를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = ReviewDetailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<ReviewDetailResponse> createReview(
            @Schema(implementation = CreateReviewRequest.class)
            @RequestBody @Valid CreateReviewRequest request
    ) {
        ReviewDetailResponse review = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review by ID", description = "리뷰 ID로 기존 리뷰 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(schema = @Schema(implementation = ReviewDetailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 리뷰가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<ReviewDetailResponse> updateReview(
            @Parameter(name = "id", description = "리뷰 ID", required = true)
            @PathVariable UUID id,

            @Schema(implementation = UpdateReviewRequest.class)
            @RequestBody @Valid UpdateReviewRequest request
    ) {
        ReviewDetailResponse review = reviewService.updateReview(id, request);
        return ResponseEntity.ok().body(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review by ID", description = "리뷰 ID로 기존 리뷰 정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공",
                    content = {}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 리뷰가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<Void> deleteReview(
            @Parameter(name = "id", description = "리뷰 ID", required = true)
            @PathVariable UUID id
    ) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Get all review lists list by movie id", description = "영화 ID가 일치하는 리뷰 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewByMovieResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 영화가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public List<ReviewByMovieResponse> getAllReviewsByMovie(
            @Parameter(name = "id", description = "영화 ID", required = true)
            @PathVariable UUID id
    ) {
        return reviewService.getAllReviewsByMovie(id);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get all review lists list by user id", description = "사용자 ID가 일치하는 리뷰 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewByMovieResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 사용자가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    public List<ReviewByUserResponse> getAllReviewsByUser(
            @Parameter(name = "id", description = "사용자 ID", required = true)
            @PathVariable UUID id
    ) {
        return reviewService.getAllReviewsByUser(id);
    }

    @Operation(summary = "Like review", description = "사용자가 리뷰에 좋아요를 표시합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {}),
            @ApiResponse(responseCode = "400", description = "이미 좋아요 표시된 리뷰입니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 사용자나 리뷰가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeReview(
            @Parameter(name = "id", description = "리뷰 ID", required = true)
            @PathVariable UUID id,

            @Parameter(name = "userId", description = "사용자 ID", required = true)
            @RequestParam UUID userId
    ) {
        reviewService.likeReview(userId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unlike review", description = "사용자가 리뷰 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {}),
            @ApiResponse(responseCode = "404", description = "좋아요 기록이 없거나, 해당 ID의 사용자나 리뷰가 존재하지 않습니다.",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikeReview(
            @Parameter(name = "id", description = "리뷰 ID", required = true)
            @PathVariable UUID id,

            @Parameter(name = "userID", description = "사용자 ID", required = true)
            @RequestParam UUID userId
    ) {
        reviewService.unlikeReview(userId, id);
        return ResponseEntity.ok().build();
    }
}
