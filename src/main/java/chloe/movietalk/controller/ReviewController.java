package chloe.movietalk.controller;

import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.dto.response.review.ReviewByMovieResponse;
import chloe.movietalk.dto.response.review.ReviewDetailResponse;
import chloe.movietalk.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDetailResponse> createReview(@RequestBody @Valid CreateReviewRequest request) {
        ReviewDetailResponse review = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDetailResponse> updateReview(@PathVariable Long id, @RequestBody @Valid UpdateReviewRequest request) {
        ReviewDetailResponse review = reviewService.updateReview(id, request);
        return ResponseEntity.ok().body(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movies/{id}")
    public List<ReviewByMovieResponse> getAllReviewsByMovie(@PathVariable Long id) {
        return reviewService.getAllReviewsByMovie(id);
    }
}
