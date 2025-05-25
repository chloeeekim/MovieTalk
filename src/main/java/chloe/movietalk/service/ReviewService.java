package chloe.movietalk.service;

import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.dto.response.review.ReviewByMovieResponse;
import chloe.movietalk.dto.response.review.ReviewByUserResponse;
import chloe.movietalk.dto.response.review.ReviewDetailResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    public List<ReviewByMovieResponse> getAllReviewsByMovie(UUID movieId);

    public List<ReviewByUserResponse> getAllReviewsByUser(UUID userId);

    public ReviewDetailResponse createReview(CreateReviewRequest request);

    public ReviewDetailResponse updateReview(UUID id, UpdateReviewRequest request);

    public void deleteReview(UUID id);

    public void likeReview(UUID userId, UUID reviewId);

    public void unlikeReview(UUID userId, UUID reviewId);
}
